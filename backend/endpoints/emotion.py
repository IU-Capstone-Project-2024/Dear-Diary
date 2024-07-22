from fastapi import APIRouter
from pydantic import BaseModel

from backend.data_models import EmotionsReportRecord
from backend.llm import generate_emotion, generate_recommendation_for_emotion
from backend.utils import sanityze_text_no_special_chars

emotionRouter = APIRouter()


class GetEmotionBody(BaseModel):
    note: str


class GetEmotionResponse(BaseModel):
    emotion: str
    recommendation: str


@emotionRouter.post("/emotion")
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


@emotionRouter.put("/emotionsReport")
async def put_emotions_report(item: PutEmotionsReportBody) -> PutEmotionsReportResponse:
    max_length = 3

    emotions = item.emotions

    # Aggregating by number of occurrences and picking the top 3
    emotions_count = {}
    for emotion in emotions:
        if emotion not in emotions_count:
            emotions_count[emotion] = 0
        emotions_count[emotion] += 1

    emotions_report = []
    for emotion, count in sorted(emotions_count.items(), key=lambda x: x[1], reverse=True):
        emotions_report.append(EmotionsReportRecord(emotion=emotion, count=count))

    return PutEmotionsReportResponse(length=min(max_length, len(emotions_report)), emotions=emotions_report[:max_length])
