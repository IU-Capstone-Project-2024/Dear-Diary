// Overlay.kt
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BodyTextStyle

@Composable
fun Overlay(
    title: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x88000000)) // Semi-transparent background
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 48.dp, vertical = 24.dp)
                .fillMaxWidth(0.6f),
            horizontalAlignment = Alignment.Start
        ) {
            BasicText(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    BasicText(text = "Yes",style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                        )
                    )
                }
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    BasicText(text = "No",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OverlayPreview() {
    Overlay(
        title = "Are you sure?",
        onConfirm = {},
        onCancel = {}
    )
}
