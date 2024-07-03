package com.example.deardairy

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.network.ApiClient
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.EmotionBox
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NewEmotionInputScreen(navController: NavHostController) {
    val apiClient = ApiClient()
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
            .padding(horizontal = 21.dp)
            .padding(bottom = 15.dp)
    ) {
        TopBar(title = "New Emotion", navController = navController)

        // EmotionBox with additional elements
        EmotionBox(
            showAdditionalInfo = true,
            showButton = true,
            textWidth = 169.dp,
            pictureWidth = 125.dp,
            pictureHeight = 125.dp,
            titleSize = 20f,
            descriptionSize = 15f,
            additionalInfoText = "Describe what you feel and Diary will tell what emotion it is",
            onButtonClick = { inputValue ->
                Log.d("NewEmotionInputScreen", "Button clicked with input: $inputValue")
                navController.navigate("loading_screen/$inputValue/new_emotion/another_screen")


//                inputValue ->
                // Navigate to NewEmotionScreen with input value
//                navController.navigate("new_emotion")
//                {
//                    // Pass inputValue as an argument
//                    this.arguments = Bundle().apply {
//                        putString("inputValue", inputValue)
//                    }
//                }
            }
        )


    }
}

@Preview
@Composable
fun NewEmotionInputScreenPreview() {
    NewEmotionInputScreen(navController = rememberNavController())
}
