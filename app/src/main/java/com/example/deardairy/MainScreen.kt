package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle

@Composable
fun CustomBoxWithTexts(
    boxHeight: Dp,
    textList: List<String>,
) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .height(180.dp)
            .background(color = Color(0xFF7788FF), shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(color = Color.Gray)
        )
        textList.forEach {
            BasicText(text = it, style = BodyTextStyle)
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dear Diary title
        BasicText(
            text = "Dear Diary",
            style = TitleTextStyle,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Emotions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
                    .height(150.dp) // Height of the horizontal container
            ) {
                // Left
//                Row(
//                    modifier = Modifier
//                        .weight(0.6f)
//                        .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                        .fillMaxSize(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column(modifier = Modifier.width(100.dp)) {
//                        BasicText(text = "What emotion do I feel", style = BodyTextStyle)
//                        Spacer(modifier = Modifier.height(16.dp))
//                        BasicText(text = "Description", style = BodyTextStyle)
//                    }
//                    // Image container
//                    Box(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
//                    )
//                }

                EmotionBox(
                    modifier = Modifier.weight(0.6f),
                    showAdditionalInfo = false,
                    showButton = false
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Right
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxSize()
                        .fillMaxWidth()
//                    .padding(16.dp)
                        .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    BasicText(
                        text = "My recent emotions",
                        style = BodyTextStyle,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
                    )
                }
            }

//            Spacer(modifier = Modifier.height(16.dp))
            // Center
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp) // Adjust height as needed
                    .background(color = Color(0xFFFFF5FF)),
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Top,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .width(170.dp)
                            .height(180.dp)
                            .background(
                                color = Color(0xFF3366AA),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp)
                            .padding(top = 40.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
//                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        )
                        BasicText(text = "Image", style = BodyTextStyle)
                    }

                    // Right column
                    CustomBoxWithTexts(
                        boxHeight = 60.dp,
                        textList = listOf("Image", "Image", "Image"),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomBoxWithTexts(
                        boxHeight = 60.dp,
                        textList = listOf("Image", "Image", "Image"),
                    )
                    CustomBoxWithTexts(
                        boxHeight = 60.dp,
                        textList = listOf("Image", "Image", "Image"),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomBoxWithTexts(
                        boxHeight = 60.dp,
                        textList = listOf("Image", "Image", "Image"),
                    )
                    CustomBoxWithTexts(
                        boxHeight = 60.dp,
                        textList = listOf("Image", "Image", "Image"),
                    )
                }

            }

                // Bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Adjust height as needed
                        .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    BasicText(
                        text = "Text at the bottom",
                        style = BodyTextStyle
                    )
                }
            }


    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
