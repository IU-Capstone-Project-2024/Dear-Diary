package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle

@Composable
fun NewEmotionScreen(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .width(42.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                )
            }
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = "New emotion",
                    style = TitleTextStyle,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
        }


        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxSize()
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.Start

        ) {
            Column (
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color(0x77224499), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start

            ) {
                BasicText(
                    text = "You emotion is",
                    style = BodyTextStyle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                BasicText(
                    text = "Anger",
                    style = BodyTextStyle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                BasicText(
                    text = "Some recommendations or whatever",
                    style = BodyTextStyle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }


            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Button(
                    onClick = onNextClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
//                        .padding(bottom = 16.dp)
//                  .align(Alignment.BottomCenter)
                ) {
                    BasicText(
                        text = "Back to the main page",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Button(
                    onClick = onNextClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
//                        .padding(bottom = 16.dp)
//                  .align(Alignment.BottomCenter)
                ) {
                    BasicText(
                        text = "Save my emotion",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun NewEmotionScreenPreview() {
    NewEmotionScreen(onNextClicked = {})
}