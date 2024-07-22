from fastapi import APIRouter, Response, status
from pydantic import BaseModel

onboardingRouter = APIRouter()


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


@onboardingRouter.post("/onboarding")
async def post_onboarding(body: PostOnboardingBody):
    # TODO
    return Response(status_code=status.HTTP_200_OK)
