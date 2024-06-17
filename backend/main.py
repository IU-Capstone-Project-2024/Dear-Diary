import requests
from dotenv import load_dotenv
import os

load_dotenv()

API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"
headers = {"Authorization": f"Bearer {os.getenv('HUGGINGFACE_API_KEY')}"}


def suggest_reflection_questions(user_note):
    context = f"""You are helpful reflection assistant. You are given a note containing person's thoughts.
    Your goal is to respond with a list of questions that can help the person process emotions, better understand the situation they are in and reassure them.
    Answer with 3 to 5 questions.
    Your response must contain only the list questions.
    Each point in a list must contain only one question.
    Reference specific details about the note in your questions.
    Avoid sentence constructions with \"and\" and \"me\".
    Be gentle and kind.""".replace("\n", " ")

    payload = {
        "inputs": f"{context} The note: \"{user_note}\"",
        "parameters": {
            "temperature": 0.8,
            "return_full_text": False,
        },
    }

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()


output = suggest_reflection_questions("I feel anxious and nervous, since it is finals week")
print(output[0]['generated_text'])

