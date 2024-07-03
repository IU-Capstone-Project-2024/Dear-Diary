package com.example.deardairy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.network.ApiClient
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun MyEmotionsLoading(navController: NavHostController,  inputValue: String, successDestination: String, failureDestination: String) {
    val apiClient = ApiClient()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "My Emotions", showLeftButton = true, navController = navController)

        PrimaryStyledContainer(verticalArrangement = Arrangement.Center){
            Text(
                text = "Wait a minute",
                style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = DarkBlueColor
                )
            )
        }
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val response = apiClient.postEmotion(inputValue)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    navController.navigate("$successDestination/${response.emotion}/${response.recommendation}")
                } else {
                    Log.e("MyEmotionsLoading", "Failed to receive response")
                    navController.navigate(failureDestination)
                }
            }
        }
    }
}

@Preview
@Composable
fun MyEmotionsLoadingPreview() {
    MyEmotionsLoading(navController = rememberNavController(), inputValue = "Sample input", successDestination = "new_emotion", failureDestination = "error_screen")
}
