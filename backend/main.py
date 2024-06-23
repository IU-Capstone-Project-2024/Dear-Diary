from dotenv import load_dotenv
from fastapi import FastAPI
from pydantic import BaseModel
from llm import generate_emotion, generate_recommendation_for_emotion

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


@app.get("/emotion")
async def get_emotion(item: GetEmotionBody):
    emotion = generate_emotion(item.note)
    recommendation = generate_recommendation_for_emotion(emotion)
    return GetEmotionResponse(emotion=emotion, recommendation=recommendation)