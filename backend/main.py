import datetime
import random

import firebase_admin
from dotenv import load_dotenv
from fastapi import FastAPI, Response, status
from firebase_admin import credentials
from firebase_admin import storage
from pydantic import BaseModel

from data_models import NoteRecord, EmotionsReportRecord
from llm import generate_emotion, generate_recommendation_for_emotion, generate_response_to_note, generate_note_title
from utils import sanityze_text_no_special_chars

load_dotenv()
app = FastAPI(
    title="Dear Diary Backend",
)

cred = credentials.Certificate("firebase-key.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'capstone-dear-diary.appspot.com'
})

bucket = storage.bucket()


example_note_schema = {
    "json_schema_extra": {
        "examples": [
            {
                "note": [{
                    "text": "i have exams and a lot of stress. What do i do?",
                    "agent": "user"
                }, {
                    "text": "I see that you feel overwhelmed. Try breathing in and out 5 times slowly.",
                    "agent": "bot"
                }, {
                    "text": "it didnt help, i am still stressed. what else can i do?",
                    "agent": "user"
                }]
            }
        ]
    }
}


class PostOnboardingBody(BaseModel):
    usage: list[str]

    model_config = {
        "json_schema_extra": {
            "examples": [
                {
                    "usage": ["Self-improvement"]
                }
            ]
        }
    }


@app.post("/onboarding")
async def post_onboarding(body: PostOnboardingBody):
    # TODO
    return Response(status_code=status.HTTP_200_OK)


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
    model_config = example_note_schema


class RespondToNoteResponse(BaseModel):
    answer: str


@app.post("/respondToNote")
async def respond_to_note(item: RespondToNoteBody) -> RespondToNoteResponse:
    # TODO: validate note records

    response = await generate_response_to_note(item.note)
    response = sanityze_text_no_special_chars(response)
    response = response.strip()
    return RespondToNoteResponse(answer=response)


class NoteTitleBody(BaseModel):
    note: list[NoteRecord]
    model_config = example_note_schema


class NoteTitleResponse(BaseModel):
    title: str


@app.post("/noteTitle")
async def note_title(item: NoteTitleBody) -> NoteTitleResponse:
    title = await generate_note_title(item.note)
    title = sanityze_text_no_special_chars(title).strip()
    return NoteTitleResponse(title=title)


class GetEmotionBody(BaseModel):
    note: str


class GetEmotionResponse(BaseModel):
    emotion: str
    recommendation: str


@app.post("/emotion")
async def get_emotion(item: GetEmotionBody) -> GetEmotionResponse:
    emotion = await generate_emotion(item.note)

    clean_emotion = emotion
    # try to find text in quotes
    if '"' in emotion:
        clean_emotion = emotion.split('"')[1]

    clean_emotion = clean_emotion.lower().capitalize()

    recommendation = await generate_recommendation_for_emotion(clean_emotion)
    recommendation = sanityze_text_no_special_chars(recommendation).strip()

    return GetEmotionResponse(emotion=clean_emotion, recommendation=recommendation)


class PutEmotionsReportBody(BaseModel):
    emotions: list[str]

    model_config = {
        "json_schema_extra": {
            "examples": [
                {
                    "emotions": ["Anger", "Sadness", "Sadness", "Happiness", "Rage"]
                }
            ]
        }
    }


class PutEmotionsReportResponse(BaseModel):
    length: int
    emotions: list[EmotionsReportRecord]


@app.put("/emotionsReport")
async def put_emotions_report(item: PutEmotionsReportBody) -> PutEmotionsReportResponse:
    max_length = 3

    emotions = item.emotions

    # aggregate by number of occurrences and pick top 3
    emotions_count = {}
    for emotion in emotions:
        if emotion not in emotions_count:
            emotions_count[emotion] = 0
        emotions_count[emotion] += 1

    emotions_report = []
    for emotion, count in sorted(emotions_count.items(), key=lambda x: x[1], reverse=True):
        emotions_report.append(EmotionsReportRecord(emotion=emotion, count=count))

    return PutEmotionsReportResponse(length=min(max_length, len(emotions_report)), emotions=emotions_report[:max_length])
