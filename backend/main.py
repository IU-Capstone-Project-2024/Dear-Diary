from dotenv import load_dotenv
from fastapi import FastAPI
from pydantic import BaseModel
from llm import generate_emotion, generate_recommendation_for_emotion, generate_response_to_note, generate_note_title
from utils import sanityze_text_letters_only, sanityze_text_no_special_chars

load_dotenv()
app = FastAPI()


@app.get("/status")
async def get_status():
    return {"status": "Up and running"}


class RespondToNoteBody(BaseModel):
    note: str


class RespondToNoteResponse(BaseModel):
    answer: str


@app.post("/respondToNote")
async def respond_to_note(item: RespondToNoteBody):
    response = generate_response_to_note(item.note)
    response = sanityze_text_no_special_chars(response)
    return RespondToNoteResponse(answer=response)


class GenerateNoteTitleBody(BaseModel):
    note: str


class GenerateNoteTitleResponse(BaseModel):
    title: str


@app.post("/noteTitle")
async def note_title(item: GenerateNoteTitleBody):
    title = generate_note_title(item.note)
    title = sanityze_text_no_special_chars(title).strip()
    return GenerateNoteTitleResponse(title=title)


class GetEmotionBody(BaseModel):
    note: str


class GetEmotionResponse(BaseModel):
    emotion: str
    recommendation: str


@app.post("/emotion")
async def get_emotion(item: GetEmotionBody):
    emotion = generate_emotion(item.note)
    emotion = sanityze_text_letters_only(emotion)

    recommendation = generate_recommendation_for_emotion(emotion).strip()
    recommendation = sanityze_text_no_special_chars(recommendation).strip()

    return GetEmotionResponse(emotion=emotion, recommendation=recommendation)
