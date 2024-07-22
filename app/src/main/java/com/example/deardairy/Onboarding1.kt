package com.example.deardairy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.GrayTextColor
import com.example.deardairy.ui.theme.LightBlueContainerColor
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily

@Composable
fun OnboardingScreen(
    screenNumber: Int,
    totalScreens: Int,
    imageResource: Int,
    titleText: String?,
    mainText: String,
    tapText: String,
    pictureSize: Dp = 150.dp,
    onScreenTap: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Dear Diary", showLeftButton = false)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .width(pictureSize),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (titleText != null) {
                    BasicText(
                        text = titleText,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = playfairDisplayFontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .padding(bottom = 0.dp)
                            .width(321.dp)
                    )
                }
                BasicText(
                    text = mainText,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(bottom = 0.dp)
                        .width(321.dp)
                )
            }

            if (screenNumber > 1) {
                Row(
                    modifier = Modifier
                        .padding(48.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(totalScreens - 1) { index ->
                        Box(
                            modifier = Modifier
//                                .size(12.dp)
                                .height(12.dp)
                                .width(20.dp)
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (index < screenNumber - 1) DarkBlueColor else LightBlueContainerColor
                                )
                        )
                    }
                }
            }

            BasicText(
                text = tapText,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = GrayTextColor,
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.BottomCenter)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onScreenTap)
            )
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        screenNumber = 2,
        totalScreens = 7,
        imageResource = R.drawable.onboarding1,
        titleText = null,
        mainText = "Hey! Now we will briefly explain you how to use our service",
        tapText = "Tap on screen to continue"
    )
}

@Composable
fun Onboarding1(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 1,
        totalScreens = 7,
        imageResource = R.drawable.onboarding1,
        titleText = null,
        mainText = "Hey! Now we will briefly explain you how to use our service",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding2") }
    )
}

@Composable
fun Onboarding2(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 2,
        totalScreens = 7,
        imageResource = R.drawable.onboarding2,
        titleText = "Create a Note",
        mainText = "Press “Add new note”",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding3") }
    )
}

@Composable
fun Onboarding3(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 3,
        totalScreens = 7,
        imageResource = R.drawable.onboarding3,
        titleText = "Get Assistance from AI",
        mainText = "Press the “life preserver” icon " +
                "and ask whatever you need",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding4") }
    )
}

@Composable
fun Onboarding4(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 4,
        totalScreens = 7,
        imageResource = R.drawable.onboarding4,
        titleText = "Understand Your Emotions",
        mainText ="Press this button, describe your condition, and get an answer",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding5") },
        pictureSize = 239.dp
    )
}

@Composable
fun Onboarding5(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 5,
        totalScreens = 7,
        imageResource = R.drawable.onboarding5,
        titleText = "View Recent Emotions and Statistics Find your recent emotions ",
        mainText = "and get statistics for a chosen period",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding6") }
    )
}

@Composable
fun Onboarding6(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 6,
        totalScreens = 7,
        imageResource = R.drawable.onboarding6,
        titleText = "Check Existing Notes",
        mainText ="Just press on a note card to view",
        tapText = "Tap on screen to continue",
        onScreenTap = { navController.navigate("onboarding7") }
    )
}


@Composable
fun Onboarding7(navController: NavHostController) {
    OnboardingScreen(
        screenNumber = 7,
        totalScreens = 7,
        imageResource = R.drawable.onboarding7,
        titleText = "Privacy Assurance",
        mainText = "Your privacy is our main priority. \n" +
                "None of your data will be sent elsewhere",
        tapText = "Tap to get started",
        onScreenTap = { navController.navigate("main_screen") }
    )
}

//android:roundIcon="@mipmap/ic_launcher_round"
