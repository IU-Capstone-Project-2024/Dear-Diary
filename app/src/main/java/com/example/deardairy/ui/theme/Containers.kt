package com.example.deardairy.ui.theme

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deardairy.R

@Composable
fun PrimaryStyledContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
            .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
            .padding(21.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun ChoiceItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BlueContainerColor, shape = RoundedCornerShape(5.dp))
            .clickable { /* Handle checkbox click */ }
            .padding(top = 5.75.dp, bottom = 5.25.dp)
            .padding(horizontal = 9.dp)
    ) {
        // Placeholder for checkbox or radio button
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = BackgroundColor, shape = RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.width(37.dp))
        BasicText(text = text, style = TextStyle(
            color = BackgroundColor,
            fontFamily = playfairDisplayFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        )
    }
}

@Composable
fun EmotionBox(
    modifier: Modifier = Modifier,
    weight: Float? = null,
    width: Dp? = null,
    textWidth: Dp = 107.dp,
    pictureWidth: Dp = 89.dp,
    pictureHeight: Dp = 83.dp,
    title: String = "What emotion do I feel?",
    titleSize: Float = 16f,
    description: String = "Short feature description",
    descriptionSize: Float = 13f,
    showAdditionalInfo: Boolean = false,
    additionalInfoText: String = "Additional Information",
    showButton: Boolean = false,
    buttonText: String = "Submit",
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
            .padding(bottom = 15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text and image container
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.width(textWidth),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = title, style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = titleSize.sp,
                    color = DarkBlueColor
                )
                )
//                Spacer(modifier = Modifier.height(16.dp))
                Text(text = description, style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = descriptionSize.sp,
                    color = DarkBlueColor)
                )
            }
            Box(
                modifier = Modifier
//                    .size(80.dp)
                    .width(pictureWidth)
                    .height(pictureHeight)
                    .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
            ){
                Image(
                    painter = painterResource(id = R.drawable.my_emotion),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(pictureWidth)
                        .height(pictureHeight)
                        .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
                )
            }
        }

        if (showAdditionalInfo) {
            // Blue container with text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 17.dp)
                    .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
                    .padding(15.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = additionalInfoText,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = BackgroundColor
                    )
                )
            }
        }

        if (showButton) {
            Spacer(modifier = Modifier.height(17.dp))
//
            CustomButton(
                buttonState = ButtonState(
                    type = ButtonType.PRIMARY,
                    text = "Let's find out!",
                    isActive = true,
                    onClickAction = {}
                )
            )

        }
    }
}

@Composable
fun CustomBoxWithTexts(
    boxHeight: Dp,
    text1: String,
    text2: String,
    text3: String
) {
    Column(
        modifier = Modifier
            .width(162.dp)
            .height(176.dp)
            .background(color = BlueContainerColor, shape = RoundedCornerShape(24.dp)),
//        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = DisabledButtonColor)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Column (
            modifier = Modifier
                .padding(horizontal = 15.dp)
//                .padding(bottom = 10.dp)
        ){
            Box {
                BasicText(
                    text = text1,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = DarkBlueColor
                    )
                )
                BasicText(
                    text = text2,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = DarkBlueColor
                    ),
                    modifier = Modifier.offset(y = (18).dp)
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
            BasicText(text = text3, style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = BackgroundColor
            ))
        }


    }
}

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
fun PrimaryStyledContainerPreview() {
//    PrimaryStyledContainer {
//        ChoiceItem(text = "Deal with emotions")
        EmotionBox(
            showAdditionalInfo = true,
            showButton = true,
            textWidth = 107.dp
        )
//        CustomBoxWithTexts(
//            boxHeight = 60.dp,
//            text1 = "Note name",
//            text2 = "Short Description",
//            text3 = "Date"
//        )
//        TopBar(title = "Hi")
//    }

//    Overlay(
//        title = "Are you sure?",
//        onConfirm = {},
//        onCancel = {}
//    )
}
