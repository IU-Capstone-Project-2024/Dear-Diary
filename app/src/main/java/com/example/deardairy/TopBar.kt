package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
//import androidx.compose.material3.icons.M3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle

@Composable
fun TopBar(
    title: String,
    showLeftButton: Boolean = true, // Параметр для показа левой кнопки (по умолчанию true)
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (showLeftButton) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Mini button instead of box
                MiniButton(onClick = onClick)
            }
        }
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = title,
                style = TitleTextStyle,
                modifier = Modifier
                    .padding(top = 42.dp, bottom = 10.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun MiniButton(
    onClick: () -> Unit,
    text: String = "Button Text"
) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(28.dp)
//            .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow), // ваше изображение
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
//                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun TopBarWithLeftButtonPreview() {
    TopBar(title = "My Emotions")
}

@Preview
@Composable
fun TopBarWithoutLeftButtonPreview() {
    TopBar(title = "My Emotions", showLeftButton = false)
}
