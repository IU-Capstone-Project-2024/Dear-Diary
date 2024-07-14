from pydantic import BaseModel


class NoteRecord(BaseModel):
    text: str
    agent: str  # "user" or "bot"


class EmotionsReportRecord(BaseModel):
    emotion: str
    count: int

