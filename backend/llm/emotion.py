from backend.llm.api import request_text_generation


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
