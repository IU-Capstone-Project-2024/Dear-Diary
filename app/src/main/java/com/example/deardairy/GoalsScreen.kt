package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.playfairDisplayFontFamily

@Composable
fun SecondScreen(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Dear Diary", showLeftButton = false)

        Box(
            modifier = Modifier
                .fillMaxWidth()
//                .fillMaxHeight()
                .weight(1f)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = "What do you hope to do in Dear Diary?",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkBlueColor,
                        fontSize = 18.sp,
                        lineHeight = 23.99.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(195.dp)
                        .padding(bottom = 16.dp)
//                        .align(Alignment.CenterHorizontally)

                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ChoiceItem(text = "Deal with emotions")
                    ChoiceItem(text = "Self-reflection")
                    ChoiceItem(text = "Other")
                }
            }

            CustomButton(
                buttonState = ButtonState(
                    type = ButtonType.PRIMARY,
                    text = "Next",
                    isActive = true,
                    onClickAction = {}
                )
            )
        }
    }
}

@Composable
private fun ChoiceItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle checkbox click */ }
            .padding(vertical = 8.dp)
    ) {
        // Placeholder for checkbox or radio button
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = Color.Blue, shape = RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicText(text = text)
    }
}

@Preview
@Composable
fun SecondScreenPreview() {
    SecondScreen(onNextClicked = {})
}