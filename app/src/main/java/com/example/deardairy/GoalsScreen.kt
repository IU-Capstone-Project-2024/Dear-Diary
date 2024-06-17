package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.ChoiceItem
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily

@Composable
fun SecondScreen(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Dear Diary", showLeftButton = false)

        PrimaryStyledContainer  {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .weight(1f)
                    .padding(bottom = 16.dp),
//                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(200.dp))
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
//                        .padding(horizontal = 21.dp)
                        .padding(bottom = 34.75.dp)
//                        .align(Alignment.CenterHorizontally)

                )
                Column(
//                    modifier = Modifier.,
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    ChoiceItem(text = "Deal with emotions")
                    ChoiceItem(text = "Self-reflection")
                    ChoiceItem(text = "Other")
                }
            }
            Spacer(modifier = Modifier.height(200.dp))
            Box {
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
}

@Preview
@Composable
fun SecondScreenPreview() {
    SecondScreen(onNextClicked = {})
}