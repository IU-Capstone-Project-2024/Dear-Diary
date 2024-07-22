package com.example.deardairy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.network.EmotionCount
import com.example.deardairy.ui.theme.BlueContainerColor
import kotlinx.coroutines.delay

@Composable
fun BlueEmotionContainer(emotionCount: EmotionCount) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = BlueContainerColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = emotionCount.emotion,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = BackgroundColor
            )
        )
        Text(
            text = emotionCount.count.toString(),
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = BackgroundColor
            )
        )
    }
}

@Composable
fun MyEmotionsAnalytics(
    navController: NavHostController,
    selectedTimePeriod: String,
    length: Int,
    emotions: List<EmotionCount>
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()
    val isLoading = remember { mutableStateOf(true) }
    val displayedEmotions = remember { mutableStateOf<List<EmotionCount>>(emptyList()) }


    LaunchedEffect(Unit) {
        // Задержка для имитации асинхронной загрузки данных
        delay(1000) // Замените это на реальный запрос к вашему API

        // Обновляем состояние после получения данных
        displayedEmotions.value = emotions
        isLoading.value = false
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "My Emotions", showLeftButton = true, navController = navController)
        {
            navController.navigate("my_emotions")
        }

        PrimaryStyledContainer{
            Column(
                modifier = Modifier
                    .padding(top = 51.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
//            Box(
            ) {
                Text(text = "Emotions statistics for", style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = DarkBlueColor
                ))
                Text(text = selectedTimePeriod, style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkBlueColor
                ),
                    modifier = Modifier.offset(y = (-12).dp))
            }
            Spacer(modifier = Modifier.height(39.dp))

          if (isLoading.value) {

            } else {
                // Отображаем контейнеры с эмоциями после загрузки
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
                ) {
                    displayedEmotions.value.forEach { emotion ->
                        BlueEmotionContainer(emotion)
                    }
                }
            }
//        }
//        // Blue container with three texts
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxSize()
//                .padding(16.dp)
//                .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
//                .padding(16.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(top = 32.dp)
//                    .fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                BasicText(text = "Emotions statistics for")
//                BasicText(text = "Today")
//            }
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .width(300.dp)
//                    .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
//            )
//
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                BasicText(text = "Anger")
//            }
            // Button at the bottom
//            Button(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxWidth()
////                    .padding(16.dp)
//            ) {
//                BasicText(
//                    text = "Next",
//                    style = BodyTextStyle,
//                )
//            }
//        }


    }
}}

//@Preview
//@Composable
//fun MyEmotionsAnalyticsPreview() {
//    MyEmotionsAnalytics(navController = rememberNavController(), selectedTimePeriod = "Last week")
//}
