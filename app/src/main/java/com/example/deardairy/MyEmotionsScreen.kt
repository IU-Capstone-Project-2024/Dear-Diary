package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle

@Composable
fun PrevEmotionContainer(
    text1: String,
    text2: String,
    text3: String
) {
    Column(
        modifier = Modifier
            .background(color = Color.Blue, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text1,
            style = BodyTextStyle,
            color = Color.White
        )
        Text(
            text = text2,
            style = BodyTextStyle,
            color = Color.White
        )
        Text(
            text = text3,
            style = BodyTextStyle,
            color = Color.White
        )
    }
}

@Composable
fun MyEmotionsScreen(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with title
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
                    text = "My emotions",
                    style = TitleTextStyle,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
        }

        // Blue container with three texts
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Text containers
                PrevEmotionContainer(text1 = "Anger", text2 = "Prompt", text3 = "Date")
                PrevEmotionContainer(text1 = "Happiness", text2 = "Prompt", text3 = "Date")
                PrevEmotionContainer(text1 = "Sadness", text2 = "Prompt", text3 = "Date")
            }
            // Button at the bottom
            Button(
                onClick = onNextClicked,
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(16.dp)
            ) {
                BasicText(
                    text = "Next",
                    style = BodyTextStyle,
                )
            }
        }


    }
}

@Preview
@Composable
fun MyEmotionsScreenPreview() {
    MyEmotionsScreen(onNextClicked = {})
}
