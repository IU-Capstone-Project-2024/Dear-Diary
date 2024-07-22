import os
import aiohttp

from backend.utils import last_user_text_from_note, note_records_to_dialog
from backend.data_models import NoteRecord

API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"
headers = {"Authorization": f"Bearer {os.getenv('HUGGINGFACE_API_KEY')}"}


async def request_text_generation(payload):
    payload["inputs"] = payload["inputs"].replace("\t", "").replace("  ", "")
    print(payload["inputs"])

    async with aiohttp.ClientSession() as session:
        async with session.post(API_URL, headers=headers, json=payload) as response:
            response_data = await response.json()

    return response_data[0]["generated_text"]


async def generate_response_to_note(note: list[NoteRecord]):
    last_user_text = last_user_text_from_note(note)
    raw_strategy = await pick_response_strategy(last_user_text)
    raw_strategy = raw_strategy.strip().lower()
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
        return await respond_questions(note, last_user_text)
    elif best_strategy_index == 1:
        return await respond_support(note, last_user_text)
    elif best_strategy_index == 2:
        return await respond_advice(note, last_user_text)
    elif best_strategy_index == 3:
        return await respond_empathy(note, last_user_text)

    # Fallback to questions if the strategy is not recognized
    return await respond_questions(note, last_user_text)


async def pick_response_strategy(last_user_text):
    payload = {
        "inputs": f"""
            **Instructions:**
            Pick the best response strategy for user's message out of 4 options:
            - "Questions" when user is unsure about their feelings and needs help processing them.
            - "Support" when user is feeling down and needs reassurance.
            - "Advice" when user is looking for guidance on how to handle a situation.
            - "Empathy" when user is feeling overwhelmed and needs to feel understood.
            
            **Input note:** "{last_user_text}"

            **Desired format:**
            Strategy: <strategy = "Question" or "Support" or "Advice" or "Empathy">

            **Output in desired format:**
            Strategy: """,
        "parameters": {
            "temperature": 0.6,
            "return_full_text": False,
            "max_new_tokens": 5,
        },
    }

    return await request_text_generation(payload)


async def process_note_template(context_prompt: str, note: list[NoteRecord]):
    note_text = note_records_to_dialog(note, skip_last_record=False)
    length = len(note)

    payload = {
        "inputs": f"""
        **Instructions:**
        {context_prompt}

        **Conversation ({length + 2} messages):**
        bot: Hello! I am a physical therapist assistant. How can I help you today?
        {note_text}
        bot (last message): """,
        "parameters": {
            "temperature": 0.9,
            "return_full_text": False,
        },
    }

    response = await request_text_generation(payload)

    if "\nuser:" in response:
        response = response.split("\nuser:")[0]

    if "\n\n" in response:
        response = response.split("\n\n")[0]

    return response


async def respond_questions(note: list[NoteRecord], last_user_text: str):
    return await process_note_template(
        f"""You are helpful reflection assistant.
        Your goal is to respond with a list of 2-3 questions that can help the person process emotions.
        Reference specific details about the note and context in your questions.
        Avoid sentence constructions with \"and\" and \"me\".
        Be gentle and kind.""",
        note)


async def respond_support(note: list[NoteRecord], last_user_text: str):
    return await process_note_template(
        f"""Your goal is to respond with a supportive message to help the person feel better.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        note)


async def respond_advice(note: list[NoteRecord], last_user_text: str):
    return await process_note_template(
        f"""You are a professional helpful advice assistant.
        Your goal is to respond with a message that provides prescience advice and guidance.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        note)


async def respond_empathy(note: list[NoteRecord], last_user_text: str):
    return await process_note_template(
        f"""You are a helpful empathy assistant.
        Your goal is to respond with an empathetic message that shows understanding and support.
        Reference specific details about the note and context in your message.
        Be gentle and kind.""",
        note)


async def generate_note_title(note: list[NoteRecord]):
    note_text = note_records_to_dialog(note, only_user_records=True)

    payload = {
        "inputs": f"""
        **Instructions:**
        You are a summarizer. Your task is to generate a summary for the given note.
        The summary should be exactly two words and neutral in tone.

        **The note:**
        {note_text}

        **Summary:**\n""",
        "parameters": {
            "temperature": 0.7,  # Lower temperature for more deterministic output
            "return_full_text": False,
            "max_new_tokens": 4,  # Adjusted to ensure brevity
        },
    }

    return await request_text_generation(payload)


async def generate_emotion(text):
    payload = {
        "inputs": f"""
        **Instructions:**
        Detect the emotion that the person is feeling based on their note.
        The emotion should be complex (like apprehensive, empowered or mellow)

        **Desired format:**
        "<only the emotion in one or two words>"

        **Input note:** "{text}"

        **Output in desired format:** \n""",
        "parameters": {
            "temperature": 1.0,
            "return_full_text": False,
            "max_new_tokens": 6,
        },
    }

    return await request_text_generation(payload)


async def generate_recommendation_for_emotion(emotion):
    payload = {
        "inputs": f"""
        **Instructions:**
        Generate a recommendation for the person based on their emotion.
        Describe how this emotion can be helpful to a person, how to navigate being in it.
        Avoid neglecting the emotion or providing advice on how to change it.
        Be gentle and kind.

        **Desired format:**
        <recommendation, 4-6 sentences>

        **Input emotion:**
        {emotion}

        **Output in desired format:** \n""",
        "parameters": {
            "temperature": 0.8,
            "return_full_text": False,
        },
    }

    response = await request_text_generation(payload)

    if "\n\n" in response:
        response = response.split("\n\n")[0]

    return response
