import re

from backend.data_models import NoteRecord


def last_user_text_from_note(note_records: list[NoteRecord]) -> str | None:
    for note_record in reversed(note_records):
        if note_record.agent == "user":
            return note_record.text
    return None


def note_records_to_dialog(note_records: list[NoteRecord], skip_last_record=False, only_user_records=False) -> str:
    records = list(note_records)

    # Used when dialog is needed only for context and the last record is provided separately
    if skip_last_record:
        records = records[:-1]

    # Used when dialog is needed only for user records
    if only_user_records:
        records = [record for record in records if record.agent == "user"]

    dialog = ""
    for note_record in records:
        dialog += f"{note_record.agent}: {note_record.text}\n"

    # Remove last \n
    dialog = dialog[:-1]

    return dialog


def sanityze_text_letters_only(text: str) -> str:
    return re.sub(r'[^a-zA-Z]', '', text)


def sanityze_text_no_special_chars(text: str) -> str:
    return re.sub(r'[^a-zA-Z0-9.,:;?!\'")(\-\n ]', '', text)
