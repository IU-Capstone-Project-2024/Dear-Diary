import os
import requests

from utils import last_user_text_from_note, note_records_to_dialog
from data_models import NoteRecord

API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"
headers = {"Authorization": f"Bearer {os.getenv('HUGGINGFACE_API_KEY')}"}


def generate_response_to_note(note: list[NoteRecord]):
    last_user_text = last_user_text_from_note(note)
    raw_strategy = pick_response_strategy(last_user_text)
    raw_strategy = raw_strategy.strip().lower()

    print()
    print()
    print(f"Strategy: {raw_strategy}")

    scores = [
        raw_strategy.find("questions"),
        raw_strategy.find("support"),
        raw_strategy.find("advice"),
        raw_strategy.find("empathy")
    ]

    best_strategy_index = 0
    min_score = 1000000
    for i, score in enumerate(scores):
        if score != -1 and score < min_score:
            min_score = score
            best_strategy_index = i

    if best_strategy_index == 0:
        return respond_questions(note, last_user_text)
    elif best_strategy_index == 1:
        return respond_support(note, last_user_text)
    elif best_strategy_index == 2:
        return respond_advice(note, last_user_text)
    elif best_strategy_index == 3:
        return respond_empathy(note, last_user_text)

    # Fallback to questions if the strategy is not recognized
    return respond_questions(note, last_user_text)


def pick_response_strategy(last_user_text):
    payload = {
        "inputs": f"""
            **Instructions:**
            Pick the best response strategy for user's message out of 4 options:
            1. 'Questions' when user is unsure about their feelings and needs help processing them.
            2. 'Support' when user is feeling down and needs reassurance.
            3. 'Advice' when user is looking for guidance on how to handle a situation.
            4. 'Empathy' when user is feeling overwhelmed and needs to feel understood.

            **Desired format:**
            Strategy: <strategy (Question or Support or Advice or Empathy)>
            <end generation>

            **Input note:** "{last_user_text}"

            **Output in desired format:** \n""",
        "parameters": {
            "temperature": 0.6,
            "return_full_text": False,
            "max_new_tokens": 5,
        },
    }

    payload["inputs"] = payload["inputs"].replace("\t", "").replace("  ", "")

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def process_note_template(context_prompt: str, format_prompt: str, note: list[NoteRecord], last_user_text: str):
    note_context = note_records_to_dialog(note, skip_last_record=True)

    payload = {
        "inputs": f"""
        **Instructions:**
        {context_prompt}
        
        **Desired format:**
        {format_prompt}
        
        **Context from previous messages:**
        {note_context}
        
        **Input note:** "{last_user_text}"
        
        **Output in desired format:** \n""",
        "parameters": {
            "temperature": 0.9,
            "return_full_text": False,
        },
    }

    payload["inputs"] = payload["inputs"].replace("\t", "").replace("  ", "")
    print(payload["inputs"])

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def respond_questions(note: list[NoteRecord], last_user_text: str):
    return process_note_template(
        f"""You are helpful reflection assistant.
        Your goal is to respond with a list of 2-3 questions that can help the person process emotions.
        Reference specific details about the note and context in your questions.
        Avoid sentence constructions with \"and\" and \"me\".
        Be gentle and kind.""",
        f"""<one-sentence empathetic intro to questions>
        1. <question1>
        2. <question2>
        3. <optional question3>""",
        note, last_user_text)


def respond_support(note: list[NoteRecord], last_user_text: str):
    return process_note_template(
        f"""Your goal is to respond with a supportive message to help the person feel better.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        f"""<empathetic message, 2-3 sentences>""",
        note, last_user_text)


def respond_advice(note: list[NoteRecord], last_user_text: str):
    return process_note_template(
        f"""You are a professional helpful advice assistant.
        Your goal is to respond with a message that provides prescience advice and guidance.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        f"""<advice message, 2-3 sentences>""",
        note, last_user_text)


def respond_empathy(note: list[NoteRecord], last_user_text: str):
    return process_note_template(
        f"""You are a helpful empathy assistant.
        Your goal is to respond with an empathetic message that shows understanding and support.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        f"""<empathetic message, 2-3 sentences>""",
        note, last_user_text)


def generate_note_title(user_note):
    context = f"""You are a summarizer. You are given a note.
    Your goal is to generate a short concise summary for the note.
    Your response must contain only the summary and be less then 4 words.""".replace("\n", " ")

    payload = {
        "inputs": f"{context} The note: \"{user_note}\". Write the summary in quotes:",
        "parameters": {
            "temperature": 1.0,
            "return_full_text": False,
            "max_new_tokens": 6,
        },
    }

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def generate_emotion(user_note):
    context = f"""You are a helpful emotion assistant. You are given a note containing person's thoughts.
    Your goal is to respond with the emotion that the person is feeling.
    Your response must contain only the emotion.
    Be gentle and kind.""".replace("\n", " ")

    payload = {
        "inputs": f"{context} The note: \"{user_note}\"",
        "parameters": {
            "temperature": 0.8,
            "return_full_text": False,
        },
    }

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def generate_recommendation_for_emotion(emotion):
    context = f"""You are a helpful emotion assistant. You are given an emotion that a person is feeling.
    Your goal is to respond with a recommendation to help them feel better.
    Your response must contain only the recommendation.
    Be gentle and kind.""".replace("\n", " ")

    payload = {
        "inputs": f"{context} The emotion: \"{emotion}\"",
        "parameters": {
            "temperature": 0.8,
            "return_full_text": False,
        },
    }

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]
