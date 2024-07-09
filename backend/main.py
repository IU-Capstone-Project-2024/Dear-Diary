import datetime
import random

from dotenv import load_dotenv
from fastapi import FastAPI
from pydantic import BaseModel

from data_models import NoteRecord
from llm import generate_emotion, generate_recommendation_for_emotion, generate_response_to_note, generate_note_title
from utils import sanityze_text_letters_only, sanityze_text_no_special_chars, note_records_to_dialog
import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage

load_dotenv()
app = FastAPI(
    title="Dear Diary Backend",
)

cred = credentials.Certificate("firebase-key.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'capstone-dear-diary.appspot.com'
})

bucket = storage.bucket()


class GetNoteCoverBody(BaseModel):
    image_id: str | None = None


class GetNoteCoverResponse(BaseModel):
    imageUrl: str
    imageId: str


@app.put("/noteCover", description="""
    When image_id is set, imageUrl will point to the image with the corresponding id (basically returning the new
    signed url). Use this when you already have the image_id, but the link has expired.
    When image_id id not set, random image will be picked. Use this for the first request to get cover image.""")
async def note_cover(body: GetNoteCoverBody) -> GetNoteCoverResponse:
    image_id = body.image_id

    if image_id is None:
        image_id = str(random.randint(1, 80))

    blob = bucket.blob(f"note_covers/{image_id}.jpg")
    url = blob.generate_signed_url(expiration=datetime.timedelta(days=60), method='GET')
    return GetNoteCoverResponse(imageUrl=url, imageId=image_id)


class StatusResponse(BaseModel):
    status: str


@app.get("/status")
async def get_status() -> StatusResponse:
    return StatusResponse(status="Up and running")


class RespondToNoteBody(BaseModel):
    note: list[NoteRecord]

    model_config = {
        "json_schema_extra": {
            "examples": [
                {
                    "note": [{
                        "text": "i have exams and a lot of stress. What do i do?",
                        "agent": "user"
                    }, {
                        "text": "breath in and out 5 times. did it help?",
                        "agent": "bot"
                    }, {
                        "text": "no, i am still stressed. what else can i do?",
                        "agent": "user"
                    }]
                }
            ]
        }
    }


class RespondToNoteResponse(BaseModel):
    answer: str


@app.post("/respondToNote")
async def respond_to_note(item: RespondToNoteBody) -> RespondToNoteResponse:
    response = generate_response_to_note(item.note)
    response = sanityze_text_no_special_chars(response)
    response = response.strip()
    return RespondToNoteResponse(answer=response)


class NoteTitleBody(BaseModel):
    note: str


class NoteTitleResponse(BaseModel):
    title: str


@app.post("/noteTitle")
async def note_title(item: NoteTitleBody) -> NoteTitleResponse:
    title = generate_note_title(item.note)
    title = sanityze_text_no_special_chars(title).strip()
    return NoteTitleResponse(title=title)


class GetEmotionBody(BaseModel):
    note: str


class GetEmotionResponse(BaseModel):
    emotion: str
    recommendation: str


@app.post("/emotion")
async def get_emotion(item: GetEmotionBody) -> GetEmotionResponse:
    emotion = generate_emotion(item.note)
    emotion = sanityze_text_letters_only(emotion)

    recommendation = generate_recommendation_for_emotion(emotion).strip()
    recommendation = sanityze_text_no_special_chars(recommendation).strip()

    return GetEmotionResponse(emotion=emotion, recommendation=recommendation)
