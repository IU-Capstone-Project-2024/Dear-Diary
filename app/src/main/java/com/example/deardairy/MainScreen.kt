package com.example.deardairy

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BlueContainerColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.CustomBoxWithTexts
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.DisabledButtonColor
import com.example.deardairy.ui.theme.EmotionBox
import com.example.deardairy.ui.theme.LightBlueContainerColor
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily

val NotesCounter =  8

@Composable
fun MainScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val emotionsBlockHeight = screenHeightDp / 6.2f
    val scrollState = rememberScrollState()
    val cardWidth = screenWidthDp/2 - 25.dp - 10.5.dp
    val cardHeight =  cardWidth * 1.092f

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // Верхняя часть
        TopBar(title = "Dear Diary", showLeftButton = false)

        // Emotions row, который также фиксирован
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(emotionsBlockHeight)
                .padding(horizontal = 21.dp)
                .padding(bottom = 17.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EmotionBox(
                modifier = Modifier
                    .clickable { navController.navigate("new_emotion_input") }
                    .width(screenWidthDp/3*2-25.dp- 10.5.dp),
                showAdditionalInfo = false,
                showButton = false,
                pictureWidth = emotionsBlockHeight - 30.dp - 12.dp,
                pictureHeight = emotionsBlockHeight - 30.dp,
                textWidth = (screenWidthDp/3*2-25.dp- 10.5.dp) - 24.dp - (emotionsBlockHeight - 30.dp - 12.dp)
            )

            Column(
                modifier = Modifier
                    .clickable { navController.navigate("my_emotions") }
                    .width(screenWidthDp / 3 - 25.dp - 10.5.dp)
                    .fillMaxSize()
                    .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 11.dp)
                    .padding(top = 19.dp, bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText(
                    text = "My recent emotions",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkBlueColor,
                        textAlign = TextAlign.Center
                    ),
                )
                Image(
                    painter = painterResource(id = R.drawable.my_emotions),
                    contentDescription = null,
                    modifier = Modifier
                        .width(emotionsBlockHeight * 0.36f)
                        .height(emotionsBlockHeight * 0.36f)
                )
            }
        }

        // Center
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
//                .height(600.dp)
                .padding(horizontal = 21.dp),
//                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    // new note
                    Column(
                        modifier = Modifier
                            .clickable { navController.navigate("new_note_screen") }
                            .width(screenWidthDp / 2 - 25.dp - 10.5.dp)
                            .height(cardHeight)
                            .background(
                                color = BlueContainerColor,
                                shape = RoundedCornerShape(24.dp)
                            )
//                            .padding(horizontal = 52.dp)
                            .padding(bottom = 11.dp)
                            .padding(top = 52.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
//                        contentAlignment = Alignment.Center
                    ) {
                        Box {
                            Icon(
                                painter = painterResource(id = R.drawable.plus), // ваше изображение
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                tint = Color.Unspecified
                            )
                        }

                        BasicText(
                            text = "Add new note", style = TextStyle(
                                fontFamily = playfairDisplayFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = BackgroundColor
                            )
                        )
                    }
                    if (NotesCounter > 0) {
                        CustomBoxWithTexts(
                            navController = navController,
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                    }
                }
            }
            if (NotesCounter > 1) {
                repeat(((NotesCounter-1) / 2) ){
                    item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomBoxWithTexts(
                            navController = navController,
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                        CustomBoxWithTexts(
                            navController = navController,
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                    } }
                }
                if (NotesCounter % 2 == 0){
                    item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        CustomBoxWithTexts(
                            navController = navController,
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                    }
                }}
            }
        }

        // Нижняя часть
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = 21.dp)
                .padding(vertical = 15.dp)
                .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 15.dp)
                .padding(vertical = 6.dp),
//                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.TopStart
        ) {
            BasicText(
                text = "Affirmation or quote of the day",
                style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = DarkBlueColor
                )
            )
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}
