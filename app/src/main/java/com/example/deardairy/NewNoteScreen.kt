package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
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

object TrapezoidShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.width * 0.7f, size.height - size.height * 0.2f)
            lineTo(size.width * 0.7f, size.height * 0.2f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun NewNoteScreen(navController: NavHostController) {
    var overlayVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .background(color = BackgroundColor)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "New Note", showLeftButton = true, navController = navController)
//        onClickAction = { overlayVisible = true }
        PrimaryStyledContainer {
            Column(verticalArrangement = Arrangement.SpaceBetween){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    BasicText(text = "Dear Diary,", style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = DarkBlueColor
                    ), modifier = Modifier.padding(bottom = 15.dp))
                    BasicText(text = "Start writing about how you feel \n" +
                            "or get any help from Diary", style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = BackgroundColor
                    ), modifier = Modifier.padding(bottom = 5.dp))

                }
                Spacer(modifier = Modifier.height(17.dp))
                CustomButton(
                    buttonState = ButtonState(
                        type = ButtonType.PRIMARY,
                        text = "Save my note",
                        isActive = true,
                        onClickAction = {navController.navigate("main_screen")}
                    )
                )
            }
            // Trapezoid container
            Box(
                modifier = Modifier
                    .size(170.dp, 70.dp)
//                    .align(Alignment.TopEnd)
                    .offset(x = (1).dp, y = (48).dp)  // Смещение для визуального выреза
                    .clip(TrapezoidShape)
                    .background(color = Color(0xFFCCE5FF))  // Добавлен красный цвет для визуализации
            )
        }

        if (overlayVisible) {
            Overlay(
                title = "Your notes will not be saved.\n" +
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
}


@Preview
@Composable
fun NewNoteScreenPreview() {
    NewNoteScreen(navController = rememberNavController())
}
