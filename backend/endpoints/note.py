import datetime
import random
from fastapi import APIRouter
from firebase_admin import storage
from pydantic import BaseModel

from backend.data_models import NoteRecord
from backend.llm import generate_response_to_note, generate_note_title
from backend.utils import sanityze_text_no_special_chars

noteRouter = APIRouter()

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


class GetNoteCoverBody(BaseModel):
    image_id: str | None = None


class GetNoteCoverResponse(BaseModel):
    imageUrl: str
    imageId: str


@noteRouter.put("/noteCover", description="""
    When image_id is set, imageUrl will point to the image with the corresponding id (basically returning the new
    signed url). Use this when you already have the image_id, but the link has expired.
    When image_id id not set, random image will be picked. Use this for the first request to get cover image.""")
async def note_cover(body: GetNoteCoverBody) -> GetNoteCoverResponse:
    image_id = body.image_id

    if image_id is None:
        image_id = str(random.randint(1, 80))

    blob = storage.bucket().blob(f"note_covers/{image_id}.jpg")
    url = blob.generate_signed_url(expiration=datetime.timedelta(days=60), method='GET')
    return GetNoteCoverResponse(imageUrl=url, imageId=image_id)


class RespondToNoteBody(BaseModel):
    note: list[NoteRecord]
    model_config = example_note_schema


class RespondToNoteResponse(BaseModel):
    answer: str


@noteRouter.post("/respondToNote")
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


@noteRouter.post("/noteTitle")
async def note_title(item: NoteTitleBody) -> NoteTitleResponse:
    title = await generate_note_title(item.note)
    title = sanityze_text_no_special_chars(title).strip()
    return NoteTitleResponse(title=title)
