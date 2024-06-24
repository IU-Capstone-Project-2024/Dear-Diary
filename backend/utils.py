import re


def sanityze_text_letters_only(text):
    return re.sub(r'[^a-zA-Z]', '', text)


def sanityze_text_no_special_chars(text):
    return re.sub(r'[^a-zA-Z0-9.,:; ]', '', text)
