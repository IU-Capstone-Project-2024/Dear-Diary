from fastapi import APIRouter
from pydantic import BaseModel

statusRouter = APIRouter()


class StatusResponse(BaseModel):
    status: str


@statusRouter.get("/status")
async def get_status() -> StatusResponse:
    return StatusResponse(status="Up and running")
