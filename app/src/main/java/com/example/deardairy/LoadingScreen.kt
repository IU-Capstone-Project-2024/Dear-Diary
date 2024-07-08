// LoadingScreen.kt

package com.example.deardairy

import android.content.Intent
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight // Import FontWeight from the correct package
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.HintTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import com.example.deardairy.ui.theme.*
import androidx.compose.material3.MaterialTheme

@Composable
fun LoadingScreen(onScreenTap: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
//            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.dd), // ваше изображение
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp), // задаем размер иконки
                tint = Color.Unspecified
            )
            BasicText(
                text = "Dear Diary",
                style = TitleTextStyle,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            BasicText(
                text = "We will try to assist you in self-reflection",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = DarkBlueColor,
//                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 0.dp)
            )
        }
        BasicText(
            text = "Tap on screen to continue",
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
        Box(modifier = Modifier.fillMaxSize().clickable(onClick = onScreenTap))
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    DearDairyTheme {
        LoadingScreen()
    }

}