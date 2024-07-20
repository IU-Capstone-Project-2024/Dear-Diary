package com.example.deardairy

import android.os.Build
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.Note
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BlueContainerColor
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.Overlay
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import com.example.deardairy.database.NoteDatabase
import com.example.deardairy.database.UserDatabase
import com.example.deardairy.network.ApiClient
import com.example.deardairy.network.StatusResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class Message(val agent: String, val text: String)

@Composable
fun UserMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = DarkBlueColor
            ),
            modifier = Modifier
//                .padding(vertical = 8.dp)
                .background(
                    color = BlueContainerColor,
                    shape = RoundedCornerShape(12.dp)
                )
//                .padding(16.dp)
        )
    }
}

@Composable
fun AssistantMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = BackgroundColor,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(top = 4.dp, bottom = 24.dp)
                .background(
                    color = BlueContainerColor,
                    shape = RoundedCornerShape(12.dp)
                )
//                .padding(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewNoteScreen(navController: NavHostController) {
    var overlayVisible by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf("") }
    val context = LocalContext.current
    val apiClient = ApiClient()
    var showErrorDialog by remember { mutableStateOf(false) }
    var apiStatus by remember { mutableStateOf<StatusResponse?>(null) }
    var helpButtonEnabled by remember { mutableStateOf(false) }
    Log.d("NewNote", "helpButtonEnabled: ${helpButtonEnabled}")
    var helpButtonColor by remember { mutableStateOf(Color.Unspecified) }
    var apiResponse by remember { mutableStateOf("") }
    var isInputEnabled by remember { mutableStateOf(true) }

    val conversationState = remember { mutableStateListOf<Message>() }
    val conversation by rememberUpdatedState(conversationState)

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = withTimeout(30000) {
                    apiClient.getStatus()
                }
                withContext(Dispatchers.Main) {
                    if (response?.status == "Up and running") {
                        apiStatus = response
                        Log.d("NewNote", "apiStatus: $apiStatus")
                    } else {
                        showErrorDialog = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showErrorDialog = true
                }
            }
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("AI assistant is unavailable") },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }


    Box(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
    ) {

    Column(modifier = Modifier
        .background(color = BackgroundColor)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "New Note", showLeftButton = true, navController = navController, onMiniButtonClick = {overlayVisible = true})


        PrimaryStyledContainer {
            Column(verticalArrangement = Arrangement.SpaceBetween){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    BasicText(text = "Dear Diary,", style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = DarkBlueColor
                    ), modifier = Modifier
                        .padding(bottom = 12.dp)
                        .width(250.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopStart)
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

                    BasicTextField(
                        value = inputValue,
                        onValueChange = { newInputValue ->
                            inputValue = newInputValue
                            helpButtonEnabled = newInputValue.isNotEmpty()
                        },
                        textStyle = TextStyle(
                            fontFamily = playfairDisplayFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = DarkBlueColor
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
//                            .height(IntrinsicSize.Min)
                            .padding(top = 24.dp, end = 32.dp),
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = isInputEnabled
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))
                CustomButton(
                    buttonState = ButtonState(
                        type = ButtonType.PRIMARY,
                        text = "Save my note",
                        isActive = true,
                        onClickAction = {
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    // Добавляем сообщение пользователя перед сохранением
                                    val initialNote = Message(agent = "user", text = inputValue)
                                    conversation.add(initialNote)
                                    inputValue = ""

                                    val noteCoverResponse = apiClient.saveNoteCover("")
                                    val coverId = noteCoverResponse?.imageId ?: ""
                                    val coverUrl = noteCoverResponse?.imageUrl ?: ""
                                    Log.d("NewNote", "coverId: ${coverId}")

                                    val conversationMapList =
                                        conversation.map { mapOf("agent" to it.agent, "text" to it.text) }
                                    Log.d("NewNote", "conversationMapList: ${conversationMapList}")
                                    val noteTitleResponse = apiClient.getNoteTitle(conversationMapList)
                                    val noteTitle = noteTitleResponse?.title ?: "New Note"
                                    Log.d("NewNote", "note Title: ${noteTitle}")

                                    val newNote = Note(
                                        name = noteTitle,  // Your logic for the note name
                                        text = conversationMapList.toString(),
                                        date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                                        coverId = coverId,
                                        coverUrl = coverUrl
                                    )

                                    val noteDatabase = NoteDatabase.getDatabase(context)
                                    Log.d("NewNote", "before insert new note")
                                    noteDatabase.noteDao().insert(newNote)
                                    Log.d("NewNote", "after insert new note")
                                    val userDatabase = UserDatabase.getDatabase(context)
                                    Log.d("NewNote", "notes: ${noteDatabase.noteDao().getAllNotes()}")
                                    var true_count = noteDatabase.noteDao().getNotesCount()
                                    userDatabase.userDao().updateNotesCounter(true_count)
                                    var count = userDatabase.userDao().getNotesCounter()
                                    Log.d("NewNote", "Notes counter: $count")

                                    withContext(Dispatchers.Main) {
                                        navController.navigate("main_screen")
                                    }
                                }catch (e: Exception) {
                                    // Handle exception
                                    e.printStackTrace()
                                }
                            }
                        }
                    )
                )
            }
        }
    }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 35.dp, top = 116.dp)
                .clickable(enabled = helpButtonEnabled) {
                    Log.d("NewNote", "clicked")
                    CoroutineScope(Dispatchers.IO).launch {
                        val initialNote = Message(agent = "user", text = inputValue)
//                        conversation.add(initialNote)
                        Log.d("NewNote", "initialNote: ${initialNote}")
                        Log.d("NewNote", "conversation: ${conversation}")
                        try {
                            val conversationMapList =
                                conversation.map { mapOf("agent" to it.agent, "text" to it.text) }
                            Log.d("NewNote", "conversationMapList: ${conversationMapList}")
                            val response = apiClient.respondToNote(conversationMapList)
                            withContext(Dispatchers.Main) {
                                if (response != null) {
                                    conversation.add(initialNote)
                                    inputValue = ""
//                                    isInputEnabled = false
                                    apiResponse = response.answer
                                    conversation.add(
                                        Message(
                                            agent = "assistant",
                                            text = response.answer
                                        )
                                    )
                                    Log.d("NewNote", "conversation: ${conversation}")
                                } else {
                                    showErrorDialog = true
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                showErrorDialog = true
                            }
                        }
                    }
                }
        ) {
        Image(
            painter = painterResource(id = R.drawable.help),
            contentDescription = "Help Icon",
            modifier = Modifier
                .height(136.dp),
            contentScale = ContentScale.Fit ,
            colorFilter = if (!helpButtonEnabled) ColorFilter.tint(helpButtonColor) else null
        )

        }
    if (overlayVisible) {
            Overlay(
                title = "Your notes will not be saved.\n" +
                        "Are you sure?",
                onConfirm = {
                            navController.navigate("main_screen")
                            overlayVisible = false
                },
                onCancel = {
                    overlayVisible = false // Hide overlay on cancel
                }
            )
        }
}}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NewNoteScreenPreview() {
    val navController = rememberNavController()
    NewNoteScreen(navController = navController)
}
