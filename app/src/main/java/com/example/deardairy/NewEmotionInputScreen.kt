package com.example.deardairy

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.network.ApiClient
import com.example.deardairy.network.StatusResponse
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.EmotionBox
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

@Composable
fun NewEmotionInputScreen(navController: NavHostController) {
    var showErrorDialog by remember { mutableStateOf(false) }
    var apiStatus by remember { mutableStateOf<StatusResponse?>(null) }
    val apiClient = ApiClient()

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = withTimeout(30000) {
                    apiClient.getStatus()
                }
                withContext(Dispatchers.Main) {
                    if (response?.status == "Up and running") {
                        apiStatus = response
                        Log.d("NewEmotionInputScreen", "apiStatus: $apiStatus")
                    } else {
                        showErrorDialog = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showErrorDialog = true
                }
            }
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("AI assistant is unavailable") },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
            .padding(bottom = 15.dp)
    ) {
        TopBar(title = "New Emotion", navController = navController)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp) // Padding for EmotionBox
        ) {
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

                }
            )
        }
    }
}

@Preview
@Composable
fun NewEmotionInputScreenPreview() {
    NewEmotionInputScreen(navController = rememberNavController())
}
