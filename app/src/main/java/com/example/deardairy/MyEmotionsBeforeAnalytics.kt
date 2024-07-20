package com.example.deardairy

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.core.i18n.DateTimeFormatter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.EmotionDatabase
import com.example.deardairy.network.ApiClient
import com.example.deardairy.network.EmotionsRequest
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun getDateRange(period: String): Pair<String, String> {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    val today = LocalDate.now()
    val startDate: LocalDate
    val endDate: LocalDate = today

    when (period) {
        "Today" -> {
            startDate = endDate
        }
        "Last week" -> {
            startDate = endDate.minus(1, ChronoUnit.WEEKS)
        }
        "Last month" -> {
            startDate = endDate.minus(1, ChronoUnit.MONTHS)
        }
        else -> {
            startDate = endDate
        }
    }

    return Pair(startDate.format(formatter), endDate.format(formatter))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyEmotionsBeforeAnalyticsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val emotionDao = EmotionDatabase.getDatabase(context).emotionDao()
    val apiClient = remember { ApiClient() }

    var selectedItem by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(selectedItem) {
        if (selectedItem.isNotEmpty()) {
            isLoading = true
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Вычисляем даты в зависимости от выбранного периода
                    val (startDate, endDate) = getDateRange(selectedItem)
                    Log.d("Emotions", "startDate: ${startDate}, endDate: ${endDate}")

                    // Получаем эмоции за указанный диапазон дат
                    val emotionsList = emotionDao.getEmotionsBetweenDates(startDate, endDate).map { it.name }
                    Log.d("Emotions", "emotionsList: ${emotionsList}")

                    val response = apiClient.putEmotions(emotionsList)
                    Log.d("Emotions", "response: ${response}")

                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            navController.popBackStack("my_emotions_loading", inclusive = true)
                            navController.navigate("my_emotions_analytics/${selectedItem}/${response.length}/${response.emotions.joinToString(",") { "${it.emotion}:${it.count}" }}")

//                            navController.navigate("my_emotions_analytics/${selectedItem}/${response.length}/${response.emotions.joinToString(",") { "${it.emotion}:${it.count}" }}")
                        } else {
                            errorMessage = "Failed to get response"
                        }
                        isLoading = false
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        errorMessage = e.localizedMessage
                        isLoading = false
                    }
                }
            }
        }
    }

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
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(250.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f),
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
//                    ChoiceItem(
//                        text = "Last month",
//                        isSelected = selectedItem == "Last month2", // Используем состояние для определения, выбран ли элемент
//                        onItemSelected = { selectedItem = "Last month2" }, // Обновляем состояние при выборе элемента
//                        radius = 20.dp
//                    )
                }
                Box {
                    CustomButton(
                        buttonState = ButtonState(
                            type = ButtonType.PRIMARY,
                            text = "Next",
                            isActive = true,
                            onClickAction = {navController.navigate("my_emotions_loading/$selectedItem")}
                        )
                    )
                }
            }


        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MyEmotionsBeforeAnalyticsScreenPreview() {
    MyEmotionsBeforeAnalyticsScreen(navController = rememberNavController())
}
