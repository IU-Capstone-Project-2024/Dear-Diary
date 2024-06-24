from dotenv import load_dotenv
from fastapi import FastAPI
from pydantic import BaseModel
from llm import generate_emotion, generate_recommendation_for_emotion
from utils import sanityze_text_letters_only, sanityze_text_no_special_chars

load_dotenv()
app = FastAPI()


class GetEmotionBody(BaseModel):
    note: str


class GetEmotionResponse(BaseModel):
    emotion: str
    recommendation: str


@app.get("/status")
async def get_status():
    return {"status": "Up and running"}


@app.post("/emotion")
async def get_emotion(item: GetEmotionBody):
    emotion = generate_emotion(item.note)
    emotion = sanityze_text_letters_only(emotion)

    recommendation = generate_recommendation_for_emotion(emotion).strip()
    recommendation = sanityze_text_no_special_chars(recommendation).strip()

    return GetEmotionResponse(emotion=emotion, recommendation=recommendation)
