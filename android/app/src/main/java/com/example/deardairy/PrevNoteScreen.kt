package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BlueContainerColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.Overlay
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily

@Composable
fun PrevNoteScreen(navController: NavHostController) {
    var overlayVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "My Notes", showLeftButton = true, navController = navController)

        PrimaryStyledContainer {
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(bottom = 17.dp)
                    .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start

            ) {
                BasicText(
                    text = "Dear Diary,",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = DarkBlueColor
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                BasicText(
                    text = "Something written by the user",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = DarkBlueColor
                    ),
//                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            CustomButton(buttonState = ButtonState(
                type = ButtonType.SECONDARY,
                text = "Burn my note!",
                isActive = true,
                onClickAction = { overlayVisible = true }
            ))
        }
    }
    if (overlayVisible) {
        Overlay(
            title = "Your note will be deleted.\n" +
                    "Are you sure?",
            onConfirm = {
                // Perform navigation or action on confirm
                navController.navigate("main_screen")
                overlayVisible = false // Hide overlay
            },
            onCancel = {
                overlayVisible = false // Hide overlay on cancel
            }
        )
    }
}

@Preview
@Composable
fun PrevNoteScreenPreview() {
    PrevNoteScreen(navController = rememberNavController())
}