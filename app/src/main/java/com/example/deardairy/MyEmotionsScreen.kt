package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.Emotion
import com.example.deardairy.database.EmotionDatabase
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.PrevEmotionContainer
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun MyEmotionsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val emotionDao = EmotionDatabase.getDatabase(context).emotionDao()
    val emotions = remember { mutableStateListOf<Emotion>() }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val emotionList = emotionDao.getAllEmotions()
            withContext(Dispatchers.Main) {
                emotions.addAll(emotionList)
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "My Emotions", showLeftButton = true, navController = navController){
            navController.navigate("main_screen")
        }

        PrimaryStyledContainer{
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.90f),
                verticalArrangement = Arrangement.spacedBy(17.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(emotions) { emotion ->
                    PrevEmotionContainer(text1 = emotion.name, text2 = "Prompt", text3 = emotion.date)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(buttonState = ButtonState(
                type = ButtonType.PRIMARY,
                text = "Analyse",
                isActive = true,
                onClickAction = { navController.navigate("my_emotions_before_analytics") }
            ))
        }
    }
}

@Preview
@Composable
fun MyEmotionsScreenPreview() {
    MyEmotionsScreen(navController = rememberNavController())
}
