package com.example.deardairy.ui.theme

import android.media.Image
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.deardairy.R
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryStyledContainer(
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 21.dp)
            .padding(horizontal = 21.dp)
            .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
            .padding(21.dp),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun ChoiceItem(text: String, isSelected: Boolean, radius: Dp = 5.dp, onItemSelected: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(36.dp)
            .fillMaxWidth()
            .background(color = BlueContainerColor, shape = RoundedCornerShape(5.dp))
            .clickable { onItemSelected() }
            .padding(horizontal = 9.dp)
    ) {
        // Placeholder for checkbox
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(color = BackgroundColor, shape = RoundedCornerShape(radius)),
            contentAlignment = Alignment.Center
        ){
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(color = DarkBlueColor, shape = RoundedCornerShape(radius))
                )
            }
        }
        Spacer(modifier = Modifier.width(37.dp))
        BasicText(
            text = text,
            style = TextStyle(
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
    pictureWidth: Dp = 89.dp,
    pictureHeight: Dp = 83.dp,
    textWidth: Dp = 107.dp,
    title: String = "What emotion do I feel?",
    titleSize: Float = 16f,
    description: String = "Short feature description",
    descriptionSize: Float = 13f,
    showAdditionalInfo: Boolean = false,
    additionalInfoText: String = "Describe what you are feeling and Diary will tell which emotion it is",
    showButton: Boolean = false,
    buttonText: String = "Submit",
    onButtonClick: (String) -> Unit = {}
) {
    var inputValue by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }


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
                .height(pictureHeight)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .height(pictureHeight)
                    .width(textWidth),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = titleSize.sp,
                        color = DarkBlueColor
                    )
                )
                Text(
                    text = description,
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = descriptionSize.sp,
                        color = DarkBlueColor
                    )
                )
            }
            Box(
                modifier = Modifier
                    .width(pictureWidth)
                    .height(pictureHeight)
                    .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
            ) {
                // Placeholder image
                Image(
                    painter = painterResource(id = R.drawable.my_emotion),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(pictureWidth)
                        .height(pictureHeight)
                        .clip(shape = RoundedCornerShape(16.dp))
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
                BasicTextField(
                    value = inputValue,
                    onValueChange = { newInputValue: String ->
                        inputValue = newInputValue
                        if (newInputValue.isEmpty()) {
                            inputValue = additionalInfoText
                        }
                    },
                    textStyle = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = BackgroundColor
                    ),
                    modifier = Modifier.fillMaxSize(),
                    interactionSource = remember { MutableInteractionSource() }
                )
            }
        }

        if (showButton) {
            Spacer(modifier = Modifier.height(17.dp))
            CustomButton(
                buttonState = ButtonState(
                    type = ButtonType.PRIMARY,
                    text = buttonText,
                    isActive = true,
                    onClickAction = { onButtonClick(inputValue) }
                )
            )
        }
    }
}


@Composable
fun CustomBoxWithTexts(
    navController: NavHostController,
    boxHeight: Dp,
    text1: String,
    text2: String,
    text3: String
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val scrollState = rememberScrollState()
    val cardWidth = screenWidthDp / 2 - 25.dp - 10.5.dp
    val cardHeight = cardWidth * 1.092f

    Column(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .background(color = BlueContainerColor, shape = RoundedCornerShape(24.dp))
            .clickable { navController.navigate("prev_note_screen") },
//        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight / 1.6f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = DisabledButtonColor)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 15.dp)
//                .padding(bottom = 10.dp)
        ) {

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
                text = text3, style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = BackgroundColor
                )
            )
        }
    }
}

@Composable
fun TopBar(
    title: String,
    showLeftButton: Boolean = true, // Параметр для показа левой кнопки (по умолчанию true)
    navController: NavHostController? = null
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
                MiniButton(onClick = { navController?.popBackStack() },)
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(28.dp)
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

@Composable
fun PrevEmotionContainer(
    text1: String,
    text2: String,
    text3: String
) {
    Column(
        modifier = Modifier
            .height(118.dp)
            .background(color = BlueContainerColor, shape = RoundedCornerShape(12.dp))
            .padding(15.dp)
            .fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text1,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = BackgroundColor
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = text2,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = DarkBlueColor
            )
        )
        Text(
            text = text3,
            style = TextStyle(
                fontFamily = playfairDisplayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = BackgroundColor
            )
        )
    }
}


@Preview
@Composable
fun PrimaryStyledContainerPreview() {
    PrimaryStyledContainer {
//        PrevEmotionContainer(text1 = "Anger", text2 = "Prompt", text3 = "Date")
//        var selectedChoice = ""
//        ChoiceItem(
//            text = "Other",
//            isSelected = selectedChoice == "Other"
//        ) {
//            selectedChoice = "Other"
//        }
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
    }

//    Overlay(
//        title = "Are you sure?",
//        onConfirm = {},
//        onCancel = {}
//    )
}
