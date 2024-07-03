package com.example.deardairy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.ButtonState
import com.example.deardairy.ui.theme.ButtonType
import com.example.deardairy.ui.theme.ChoiceItem
import com.example.deardairy.ui.theme.CustomButton
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.PrimaryStyledContainer
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.TypeConverter
import com.example.deardairy.database.initializeUser
import com.example.deardairy.database.NoteDatabase
import com.example.deardairy.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun GoalsScreen(navController: NavHostController) {
    val selectedChoices = remember { mutableStateListOf<String>() }
    var isNextButtonEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current


    fun updateSelectedChoices(choice: String) {
        if (selectedChoices.contains(choice)) {
            selectedChoices.remove(choice)
        } else {
            selectedChoices.add(choice)
        }
        isNextButtonEnabled = selectedChoices.isNotEmpty() // Обновление состояния кнопки "Next"
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Dear Diary", showLeftButton = false)

        PrimaryStyledContainer(verticalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .weight(1f)
//                    .padding(bottom = 16.dp),
//                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(240.dp))
                BasicText(
                    text = "What do you hope to do in Dear Diary?",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkBlueColor,
                        fontSize = 18.sp,
                        lineHeight = 23.99.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(195.dp)
                        .padding(bottom = 34.75.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    ChoiceItem(
                        text = "Deal with emotions",
                        isSelected = selectedChoices.contains("Deal with emotions"),
                    ) {
                        updateSelectedChoices("Deal with emotions")
                    }
                    ChoiceItem(
                        text = "Self-reflection",
                        isSelected = selectedChoices.contains("Self-reflection")
                    ) {
                        updateSelectedChoices("Self-reflection")
                    }
                    ChoiceItem(
                        text = "Other",
                        isSelected = selectedChoices.contains("Other")
                    ) {
                        updateSelectedChoices("Other")
                    }
                }
            }
            Spacer(modifier = Modifier.height(200.dp))
            Box {
                if (isNextButtonEnabled) {
                    CustomButton(
                        buttonState = ButtonState(
                            type = ButtonType.PRIMARY,
                            text = "Next",
                            isActive = true,
                            onClickAction = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val userDatabase = UserDatabase.getDatabase(context)
                                    val userDao = userDatabase.userDao()

                                    val user = userDao.getUserById(0)
                                    Log.d("GoalsScreen", "Before update: $user")

                                    userDao.updateGoals(selectedChoices.joinToString(","))

                                    val updatedUser = userDao.getUserById(0)
                                    Log.d("GoalsScreen", "After update:  $updatedUser")

                                    withContext(Dispatchers.Main) {
                                        Log.d("GoalsScreen", "before nav to main screen")
                                        navController.navigate("main_screen")
                                    }
                                }
                            }
                        )
                    )
                } else {
                    CustomButton(
                        buttonState = ButtonState(
                            type = ButtonType.PRIMARY,
                            text = "Next",
                            isActive = false,
                            onClickAction = {}
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GoalsScreenPreview() {
    val navController = rememberNavController()
    GoalsScreen(navController = navController)
}