package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.deardairy.ui.theme.BodyTextStyle

@Composable
fun EmotionBox(
    modifier: Modifier = Modifier,
    weight: Float = 0.6f, // Default weight set to 0.6f
    text: String = "What emotion do I feel",
    description: String = "Description",
    showAdditionalInfo: Boolean = false,
    additionalInfoText: String = "Additional Information",
    showButton: Boolean = false,
    buttonText: String = "Submit",
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
//            .weight(weight)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text and image container at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(100.dp)) {
                Text(text = text, style = BodyTextStyle)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = description, style = BodyTextStyle)
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
            )
        }

        if (showAdditionalInfo) {
            // Blue container with text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF3366AA), shape = RoundedCornerShape(8.dp))
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = additionalInfoText,
                    style = BodyTextStyle,
                    color = Color.White
                )
            }
        }

        if (showButton) {
            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3366AA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buttonText,
                    style = BodyTextStyle,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun EmotionBoxPreview() {
    EmotionBox(
        showAdditionalInfo = true,
        showButton = true
    )
}

