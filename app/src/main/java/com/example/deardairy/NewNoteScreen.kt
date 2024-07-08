package com.example.deardairy

import android.os.Build
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.Note
import com.example.deardairy.database.NoteDao
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
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deardairy.database.AppDatabase
import com.example.deardairy.database.NoteDatabase
import com.example.deardairy.database.UserDatabase
import com.example.deardairy.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewNoteScreen(navController: NavHostController) {
    var overlayVisible by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val apiClient = ApiClient()


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
                        .padding(bottom = 15.dp)
                        .width(250.dp))
                    BasicTextField(
                        value = inputValue,
                        onValueChange = { newInputValue: String ->
                            inputValue = newInputValue
                        },
                        textStyle = TextStyle(
                            fontFamily = playfairDisplayFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = BackgroundColor
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .width(250.dp),
                        interactionSource = remember { MutableInteractionSource() }
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
                                    val noteCoverResponse = apiClient.saveNoteCover("")
                                    val coverId = noteCoverResponse?.imageId ?: ""
                                    val coverUrl = noteCoverResponse?.imageUrl ?: ""
                                    Log.d("NewNote", "coverId: ${coverId}")


                                    val noteTitleResponse = apiClient.getNoteTitle(inputValue)
                                    val noteTitle = noteTitleResponse?.title ?: "New Note"
                                    Log.d("NewNote", "note Title: ${noteTitle}")


                                    val newNote = Note(
                                        name = noteTitle,  // Your logic for the note name
                                        text = inputValue,
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
        Image(
            painter = painterResource(id = R.drawable.help),
            contentDescription = "Help Icon",
            modifier = Modifier
                .height(136.dp)
                .clickable {
                }
                .padding(horizontal = 35.dp)
                .offset(y = 116.dp)
                .align(Alignment.TopEnd),
            contentScale = ContentScale.Fit
        )
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
