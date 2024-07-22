import firebase_admin
from dotenv import load_dotenv
from fastapi import FastAPI
from firebase_admin import credentials

from backend.endpoints.emotion import emotionRouter
from backend.endpoints.note import noteRouter
from backend.endpoints.status import statusRouter
from backend.endpoints.onboarding import onboardingRouter

load_dotenv()

cred = credentials.Certificate("firebase-key.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'capstone-dear-diary.appspot.com'
})

app = FastAPI(
    title="Dear Diary Backend",
)

app.include_router(statusRouter)
app.include_router(onboardingRouter)
app.include_router(noteRouter)
app.include_router(emotionRouter)
