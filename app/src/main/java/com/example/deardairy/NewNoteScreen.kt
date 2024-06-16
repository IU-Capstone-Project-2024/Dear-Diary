package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle

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
fun NewNoteScreen(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with a back button and title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .width(42.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                )
            }
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = "New Note",
                    style = TitleTextStyle,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
        }

        // Light blue container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Inner blue container with text
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(color = Color(0xFF7788FF), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        BasicText(text = "Note Title", style = BodyTextStyle, modifier = Modifier.padding(bottom = 8.dp))
                        BasicText(text = "Note Content", style = BodyTextStyle)
                    }
                }

                // Save button
                Button(
                    onClick = onNextClicked,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    BasicText(
                        text = "Save my note",
                        style = BodyTextStyle.copy(color = Color.White)
                    )
                }
            }

            // Trapezoid container
            Box(
                modifier = Modifier
                    .size(170.dp, 70.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (1).dp, y = (48).dp)  // Смещение для визуального выреза
                    .clip(TrapezoidShape)
                    .background(color = Color(0xFFCCE5FF))  // Добавлен красный цвет для визуализации
            )
        }
    }
}

@Preview
@Composable
fun NewNoteScreenPreview() {
    NewNoteScreen(onNextClicked = {})
}
