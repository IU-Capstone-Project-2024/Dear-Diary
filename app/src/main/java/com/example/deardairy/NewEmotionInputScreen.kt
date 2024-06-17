package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.EmotionBox
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar

@Composable
fun NewEmotionInputScreen() {
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
            .padding(horizontal = 21.dp)
            .padding(bottom = 15.dp)
    ) {
        TopBar(title = "New Emotion")

        // EmotionBox with additional elements
        EmotionBox(
            showAdditionalInfo = true,
            showButton = true,
            textWidth = 169.dp,
            pictureWidth = 125.dp,
            pictureHeight = 125.dp,
            titleSize = 20f,
            descriptionSize = 15f,
            additionalInfoText = "Describe what you feel and Diary will tell what emotion it is"
        )


    }
}

@Preview
@Composable
fun NewEmotionInputScreenPreview() {
    NewEmotionInputScreen()
}
