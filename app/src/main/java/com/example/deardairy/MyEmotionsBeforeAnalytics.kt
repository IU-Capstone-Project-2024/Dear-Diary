package com.example.deardairy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.ChoiceItem
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrevEmotionContainer
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily


@Composable
fun MyEmotionsBeforeAnalyticsScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(title = "My Emotions", showLeftButton = true, navController = navController)

        PrimaryStyledContainer(verticalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(250.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
//                    ChoiceItem(text = "Today", )
//                    ChoiceItem(text = "Last week")
//                    ChoiceItem(text = "last month")
//                    ChoiceItem(text = "Last month")
                    ChoiceItem(
                        text = "Today",
                        isSelected = selectedItem == "Today", // Используем состояние для определения, выбран ли элемент
                        onItemSelected = { selectedItem = "Today" }, // Обновляем состояние при выборе элемента
                        radius = 20.dp
                    )
                    ChoiceItem(
                        text = "Last week",
                        isSelected = selectedItem == "Last week", // Используем состояние для определения, выбран ли элемент
                        onItemSelected = { selectedItem = "Last week" }, // Обновляем состояние при выборе элемента
                        radius = 20.dp
                    )
                    ChoiceItem(
                        text = "Last month",
                        isSelected = selectedItem == "Last month", // Используем состояние для определения, выбран ли элемент
                        onItemSelected = { selectedItem = "Last month" }, // Обновляем состояние при выборе элемента
                        radius = 20.dp
                    )
                    ChoiceItem(
                        text = "Last month",
                        isSelected = selectedItem == "Last month2", // Используем состояние для определения, выбран ли элемент
                        onItemSelected = { selectedItem = "Last month2" }, // Обновляем состояние при выборе элемента
                        radius = 20.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(220.dp))
            Box {
                CustomButton(
                    buttonState = ButtonState(
                        type = ButtonType.PRIMARY,
                        text = "Next",
                        isActive = true,
                        onClickAction = {navController.navigate("my_emotions_loading")}
                    )
                )
            }

        }
    }
}

@Preview
@Composable
fun MyEmotionsBeforeAnalyticsScreenPreview() {
    MyEmotionsBeforeAnalyticsScreen(navController = rememberNavController())
}
