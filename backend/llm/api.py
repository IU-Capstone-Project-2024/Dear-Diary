import os
import aiohttp

API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"
headers = {"Authorization": f"Bearer {os.getenv('HUGGINGFACE_API_KEY')}"}


async def request_text_generation(payload):
    payload["inputs"] = payload["inputs"].replace("\t", "").replace("  ", "")
    print(payload["inputs"])

    async with aiohttp.ClientSession() as session:
        async with session.post(API_URL, headers=headers, json=payload) as response:
            response_data = await response.json()

    return response_data[0]["generated_text"]
