package com.example.deardairy

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
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import kotlinx.coroutines.delay


@Composable
fun MyEmotionsLoading(navController: NavHostController, selectedTimePeriod: String) {
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
        delay(2000) // Задержка на 2 секунды
        navController.navigate("my_emotions_analytics/$selectedTimePeriod")
    }
}

@Preview
@Composable
fun MyEmotionsLoadingPreview() {
    MyEmotionsLoading(navController = rememberNavController(), selectedTimePeriod = "Today")
}
