package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.TitleTextStyle


@Composable
fun MyEmotionsAnalytics(onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with title
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
                    text = "My emotions",
                    style = TitleTextStyle,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
        }

        // Blue container with three texts
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color(0xFFCCE5FF), shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(text = "Emotions statistics for")
                BasicText(text = "Today")
            }

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(text = "Anger")
            }
            // Button at the bottom
            Button(
                onClick = onNextClicked,
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(16.dp)
            ) {
                BasicText(
                    text = "Next",
                    style = BodyTextStyle,
                )
            }
        }


    }
}

@Preview
@Composable
fun MyEmotionsAnalyticsPreview() {
    MyEmotionsAnalytics(onNextClicked = {})
}
