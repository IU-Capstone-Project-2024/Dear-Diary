from pydantic import BaseModel


class NoteRecord(BaseModel):
    text: str
    agent: str  # "user" or "bot"

