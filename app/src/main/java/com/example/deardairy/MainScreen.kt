package com.example.deardairy

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

val NotesCounter =  4

@Composable
fun MainScreen() {
    val scrollState = rememberScrollState()

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
                .height((112+17).dp) // Height of the horizontal container
                .padding(horizontal = 21.dp)
                .padding(bottom = 17.dp),
        ) {
            EmotionBox(
                modifier = Modifier.width(239.dp),
                showAdditionalInfo = false,
                showButton = false
            )

            Spacer(modifier = Modifier.width(25.dp))

            Column(
                modifier = Modifier
                    .width(85.dp)
                    .fillMaxSize()
                    .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 11.dp)
                    .padding(top = 19.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BasicText(
                    text = "My recent emotions",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkBlueColor
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.my_emotions),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
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
                            .width(162.dp)
                            .height(176.dp)
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
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                    }
                    // Right column
                }
            }
            if (NotesCounter > 1) {
                repeat((NotesCounter / 2)-1){
                    item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomBoxWithTexts(
                            boxHeight = 60.dp,
                            text1 = "Note name",
                            text2 = "Short Description",
                            text3 = "Date"
                        )
                        CustomBoxWithTexts(
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
    MainScreen()
}
