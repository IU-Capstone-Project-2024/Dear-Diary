import os
import requests


API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"
headers = {"Authorization": f"Bearer {os.getenv('HUGGINGFACE_API_KEY')}"}


def generate_response_to_note(user_note):
    raw_strategy = pick_response_strategy(user_note)
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
        return respond_questions(user_note)
    elif best_strategy_index == 1:
        return respond_support(user_note)
    elif best_strategy_index == 2:
        return respond_advice(user_note)
    elif best_strategy_index == 3:
        return respond_empathy(user_note)

    # Fallback to questions if the strategy is not recognized
    return respond_questions(user_note)


def pick_response_strategy(user_note):
    instruction = f"""You are a helpful assistant picking the right response strategy.
    You are given a note containing person's thoughts.
    Your response must contain only a single word: the name of the response strategy.
    Options:
    1. Answer 'Questions' when user is unsure about their feelings and needs help processing them.
    2. Answer 'Support' when user is feeling down and needs reassurance.
    3. Answer 'Advice' when user is looking for guidance on how to handle a situation.
    4. Answer 'Empathy' when user is feeling overwhelmed and needs to feel understood.""".replace("\n", " ")

    payload = {
        "inputs": f"{instruction} The note: \"{user_note}\". Strategy: ",
        "parameters": {
            "temperature": 0.5,
            "return_full_text": False,
        },
    }

    print(payload)

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def process_note_template(instruction, user_note):
    clean_instruction = instruction.replace("\n", " ")

    payload = {
        "inputs": f"{clean_instruction} The note: \"{user_note}\"",
        "parameters": {
            "temperature": 0.8,
            "return_full_text": False,
        },
    }

    response = requests.post(API_URL, headers=headers, json=payload)
    return response.json()[0]["generated_text"]


def respond_questions(user_note):
    return process_note_template(f"""You are helpful reflection assistant. You are given a note containing person's thoughts.
    Your goal is to respond with a list of questions that can help the person process emotions, better understand the situation they are in and reassure them.
    Answer with 3 to 5 questions.
    Your response must contain only the list questions.
    Each point in a list must contain only one question.
    Reference specific details about the note in your questions.
    Avoid sentence constructions with \"and\" and \"me\".
    Be gentle and kind.""", user_note)


def respond_support(user_note):
    return process_note_template(f"""You are a helpful support assistant. You are given a note containing person's thoughts.
    Your goal is to respond with a supportive message that can help the person feel better.
    Your response must contain only the supportive message.
    Reference specific details about the note in your message.
    Avoid sentence constructions with \"and\" and \"me\".
    Be gentle and kind.""", user_note)


def respond_advice(user_note):
    return process_note_template(f"""You are a helpful advice assistant. You are given a note containing person's thoughts.
    Your goal is to respond with advice on how to handle the situation.
    Your response must contain only the advice.
    Reference specific details about the note in your advice.
    Avoid sentence constructions with \"and\" and \"me\".
    Be gentle and kind.""", user_note)


def respond_empathy(user_note):
    return process_note_template(f"""You are a helpful empathy assistant. You are given a note containing person's thoughts.
    Your goal is to respond with a message that shows understanding and empathy.
    Your response must contain only the empathetic message.
    Reference specific details about the note in your message.
    Avoid sentence constructions with \"and\" and \"me\".
    Be gentle and kind.""", user_note)


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
