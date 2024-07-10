import re

from data_models import NoteRecord


def last_user_text_from_note(note_records: list[NoteRecord]) -> str | None:
    for note_record in reversed(note_records):
        if note_record.agent == "user":
            return note_record.text
    return None


def note_records_to_dialog(note_records: list[NoteRecord], skip_last_user_text=False) -> str:
    dialog = ""
    for note_record in note_records:
        dialog += f"{note_record.agent}: {note_record.text}\n"

    if skip_last_user_text:
        dialog = dialog[:dialog.rfind("user:")]

    return dialog


def sanityze_text_letters_only(text: str) -> str:
    return re.sub(r'[^a-zA-Z]', '', text)


def sanityze_text_no_special_chars(text: str) -> str:
    return re.sub(r'[^a-zA-Z0-9.,:;?!\'")(\-\n ]', '', text)
