// ButtonStyles.kt

package com.example.deardairy.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Enum для типа кнопки
enum class ButtonType {
    PRIMARY,
    SECONDARY
}

// Класс для хранения состояния кнопки
data class ButtonState(
    val type: ButtonType,
    val text: String,
    val isActive: Boolean,
    val onClickAction: () -> Unit
)

@Composable
fun CustomButton(buttonState: ButtonState) {
    // Определяем цвета для разных состояний кнопки
    val backgroundColor = if (buttonState.isActive) {
        when (buttonState.type) {
            ButtonType.PRIMARY -> DarkBlueColor
            ButtonType.SECONDARY -> Color.Transparent
        }
    } else {
        when (buttonState.type) {
            ButtonType.PRIMARY -> DisabledButtonColor
            ButtonType.SECONDARY -> Color.Transparent
        }
    }

    val contentColor = if (buttonState.isActive) {
        when (buttonState.type){
            ButtonType.PRIMARY -> BackgroundColor
            ButtonType.SECONDARY -> DarkBlueColor
        }
    } else {
        when (buttonState.type){
            ButtonType.PRIMARY -> BackgroundColor
            ButtonType.SECONDARY -> DisabledButtonColor
        }
    }

    val borderStroke = if (buttonState.type == ButtonType.SECONDARY) {
        if (buttonState.isActive) {
            BorderStroke(1.dp, DarkBlueColor)
        } else {
            BorderStroke(1.dp, DisabledButtonColor)
        }
    } else {
        null
    }

    Button(
        onClick = buttonState.onClickAction,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = borderStroke,
        shape = shapes.medium.copy(
            topStart = CornerSize(5.dp),
            topEnd = CornerSize(5.dp),
            bottomStart = CornerSize(5.dp),
            bottomEnd = CornerSize(5.dp)
        ),
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
            .height(45.dp)
//            .padding(horizontal = 16.dp)
    ) {
        Text(text = buttonState.text, style = ButtonTextStyle)
    }
}


@Preview
@Composable
fun CustomButtonPreview() {
    val primaryActiveButtonState = ButtonState(
        type = ButtonType.PRIMARY,
        text = "Primary Active",
        isActive = true,
        onClickAction = {}
    )

    val secondaryActiveButtonState = ButtonState(
        type = ButtonType.SECONDARY,
        text = "Secondary Active",
        isActive = true,
        onClickAction = {}
    )

    val primaryDisabledButtonState = ButtonState(
        type = ButtonType.PRIMARY,
        text = "Primary Disabled",
        isActive = false,
        onClickAction = {}
    )

    val secondaryDisabledButtonState = ButtonState(
        type = ButtonType.SECONDARY,
        text = "Secondary Disabled",
        isActive = false,
        onClickAction = {}
    )
    Column {
        CustomButton(buttonState = primaryActiveButtonState)
        CustomButton(buttonState = primaryDisabledButtonState)
        CustomButton(buttonState = secondaryActiveButtonState)
        CustomButton(buttonState = secondaryDisabledButtonState)
    }

}