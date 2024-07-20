package com.example.deardairy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.Note
import com.example.deardairy.database.NoteDatabase
import com.example.deardairy.database.UserDatabase
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BlueContainerColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.Overlay
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

fun parseMessages(text: String): List<Message> {
    val cleanedText = text.trim().removePrefix("[").removeSuffix("]").trim()

    val messageParts = cleanedText.split("}, {")
        .map { it.trim().removePrefix("{").removeSuffix("}") }

    return messageParts.mapNotNull { part ->
        val agentRegex = """agent\s*=\s*(\w+)""".toRegex()
        val textRegex = """text\s*=\s*(.*)""".toRegex(RegexOption.DOT_MATCHES_ALL)

        val agentMatch = agentRegex.find(part)
        val textMatch = textRegex.find(part)

        val agent = agentMatch?.groupValues?.get(1)
        val messageText = textMatch?.groupValues?.get(1)?.trim()

        if (agent != null && messageText != null) {
            Message(agent, messageText)
        } else {
            null
        }
    }
}


@Composable
fun PrevNoteScreen(navController: NavHostController, noteId: Long) {
    var overlayVisible by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf<Note?>(null) }
    val context = LocalContext.current

    LaunchedEffect(noteId) {
        CoroutineScope(Dispatchers.IO).launch {
            var noteDatabase = NoteDatabase.getDatabase(context)
            try{
                Log.d("PrevNoteScreen", "NoteId prevnotescreen: ${noteId}")
                note = noteDatabase.noteDao().getNoteById(noteId ?: 1)
                Log.d("PrevNoteScreen", "Note: ${note}")
            } catch (e: Exception){
                Log.e("PrevNoteScreen", "Error getting note", e)
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "My Notes", showLeftButton = true, navController = navController)
        PrimaryStyledContainer {
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(bottom = 17.dp)
                    .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start

            ) {
                BasicText(
                    text = "Dear Diary,",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = DarkBlueColor
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                note?.let { note ->
                    Log.d("PrevNoteScreen", "Here")

                    // Разбираем строку на сообщения
                    val conversation = parseMessages(note.text)
                    Log.d("PrevNoteScreen", "conversation: $conversation")

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 32.dp)
                    ) {
                        items(conversation) { message ->
                            if (message.agent == "user") {
                                UserMessage(message.text)
                            } else if (message.agent == "assistant") {
                                AssistantMessage(message.text)
                            }
                        }
                    }
                }


            }
            CustomButton(buttonState = ButtonState(
                type = ButtonType.SECONDARY,
                text = "Burn my note!",
                isActive = true,
                onClickAction = { overlayVisible = true }
            ))
        }
    }
    if (overlayVisible) {
        Overlay(
            title = "Your note will be deleted.\n" +
                    "Are you sure?",
            onConfirm = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val noteDatabase = NoteDatabase.getDatabase(context)
                        noteDatabase.noteDao().deleteNoteById(noteId)
                        withContext(Dispatchers.Main) {
                            navController.navigate("main_screen")
                            overlayVisible = false // Hide overlay
                        }
                    } catch (e: Exception) {
                        Log.e("PrevNoteScreen", "Error deleting note", e)
                    }
                }
            },
            onCancel = {
                overlayVisible = false // Hide overlay on cancel
            }
        )
    }
}

@Preview
@Composable
fun PrevNoteScreenPreview() {
    PrevNoteScreen(navController = rememberNavController(), 1)
}