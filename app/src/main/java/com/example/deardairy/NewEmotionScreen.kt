package com.example.deardairy

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Overlay
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
import com.example.deardairy.database.Emotion
import com.example.deardairy.database.EmotionDatabase
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
import com.example.deardairy.ui.theme.PrimaryStyledContainerPreview
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//import com.example.deardairy.ui.theme.


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewEmotionScreen(navController: NavHostController, emotion: String, recommendation: String) {
    var overlayVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top

    ) {
        TopBar(title = "New Emotion", showLeftButton = true, navController = navController)

        PrimaryStyledContainer{
            Column (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .fillMaxWidth()
//                    .padding(16.dp)
                    .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                BasicText(
                    text = "You emotion is",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = BackgroundColor
                    ),
//                    modifier = Modifier.padding(bottom = 16.dp)
                )

                BasicText(
                    text = emotion,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp,
                        color = BackgroundColor
                    ),
//                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))

                BasicText(
                    text = recommendation,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = DarkBlueColor
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(17.dp))

            Column(
//                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                CustomButton(buttonState = ButtonState(
                    type = ButtonType.SECONDARY,
                    text = "Back to the main page",
                    isActive = true,
                    onClickAction =  { overlayVisible = true }
                ))



                CustomButton(buttonState = ButtonState(
                    type = ButtonType.PRIMARY,
                    text = "Save my emotion",
                    isActive = true,
                    onClickAction = {
                        val newEmotion = Emotion(
                            name = emotion,  // Your logic for the note name
                            text = recommendation,
                            date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            val emotionDatabase = EmotionDatabase.getDatabase(context)
                            Log.d("NewEmotion", "before insert new emotion")
                            emotionDatabase.emotionDao().insertEmotion(newEmotion)
                            Log.d("NewEmotion", "after insert new emotion")
                            val userDatabase = UserDatabase.getDatabase(context)
                            Log.d("NewEmotion", "notes: ${emotionDatabase.emotionDao().getAllEmotions()}")
                            try {
                                var true_count = emotionDatabase.emotionDao().getEmotionCount()
                                userDatabase.userDao().updateEmotionsCounter(true_count)
//                                Log.d("NewEmotion", "emotions counter: ${userDatabase.userDao().getEmotionsCounter()}")
                            } finally {
                                var count = userDatabase.userDao().getEmotionsCounter()
                                Log.d("NewNote", "Emotion counter: $count")
                            }
                            withContext(Dispatchers.Main) {
                                navController.navigate("my_emotions")
                            }
                        }

                    }
                ))
                
            }
        }
    }
    if (overlayVisible) {
        Overlay(
            title = "Are you sure you want to go back to the main page?",
            onConfirm = {
                // Perform navigation or action on confirm
                navController.navigate("main_screen")
                overlayVisible = false // Hide overlay
            },
            onCancel = {
                overlayVisible = false // Hide overlay on cancel
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NewEmotionScreenPreview() {
    NewEmotionScreen(navController = rememberNavController(), emotion = "Sadness", recommendation = "Recommendation: Practice self-care by taking a warm bath...")
}