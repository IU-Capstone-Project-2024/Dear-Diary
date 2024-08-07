package com.example.deardairy

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.deardairy.network.EmotionCount
import com.example.deardairy.util.PreferencesUtil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = navController.context

    NavHost(
        navController = navController,
        startDestination = "loading_screen",
        modifier = modifier
    ) {
        composable("loading_screen") {
            LoadingScreen(navController = navController, onScreenTap = {
            })
        }
        composable("goals_screen") {
            GoalsScreen(navController = navController)
        }
        composable("onboarding1") { Onboarding1(navController) }
        composable("onboarding2") { Onboarding2(navController) }
        composable("onboarding3") { Onboarding3(navController) }
        composable("onboarding4") { Onboarding4(navController) }
        composable("onboarding5") { Onboarding5(navController) }
        composable("onboarding6") { Onboarding6(navController) }
        composable("onboarding7") { Onboarding7(navController) }
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable("my_emotions") {
            MyEmotionsScreen(navController = navController)
        }
        composable("new_emotion_input") { NewEmotionInputScreen(navController) }
        composable("loading_screen/{inputValue}/{successDestination}/{failureDestination}") { backStackEntry ->
            val inputValue = backStackEntry.arguments?.getString("inputValue") ?: ""
            val successDestination = backStackEntry.arguments?.getString("successDestination") ?: ""
            val failureDestination = backStackEntry.arguments?.getString("failureDestination") ?: ""
            MyEmotionsLoading(navController, inputValue, successDestination, failureDestination)
        }
        composable("new_emotion/{emotion}/{recommendation}") { backStackEntry ->
            val emotion = backStackEntry.arguments?.getString("emotion") ?: ""
            val recommendation = backStackEntry.arguments?.getString("recommendation") ?: ""
            NewEmotionScreen(navController, emotion ?: "Unknown Emotion", recommendation ?: "No recommendation available")
        }

        composable("new_note_screen") {
            NewNoteScreen(navController = navController)
        }

        composable("my_emotions_before_analytics") {
            MyEmotionsBeforeAnalyticsScreen(navController)
        }
        composable("my_emotions_before_analytics") { backStackEntry ->
            MyEmotionsBeforeAnalyticsScreen(navController)
        }
        composable("my_emotions_analytics/{selectedTimePeriod}/{length}/{emotions}") { backStackEntry ->
            val selectedTimePeriod = backStackEntry.arguments?.getString("selectedTimePeriod")
            val length = backStackEntry.arguments?.getString("length")?.toIntOrNull()
            val emotionsString = backStackEntry.arguments?.getString("emotions")
            val emotions = emotionsString?.split(",")?.map {
                val (emotion, count) = it.split(":")
                EmotionCount(emotion, count.toInt())
            }

            if (selectedTimePeriod != null && length != null && emotions != null) {
                MyEmotionsAnalytics(navController, selectedTimePeriod, length, emotions)
            }
        }
        composable(
            "prev_note_screen/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 0
            PrevNoteScreen(navController = navController, noteId = noteId)
        }
        composable("new_emotion/{emotion}/{recommendation}") { backStackEntry ->
            val emotion = backStackEntry.arguments?.getString("emotion")
            val recommendation = backStackEntry.arguments?.getString("recommendation")
            NewEmotionScreen(navController, emotion ?: "Unknown Emotion", recommendation ?: "No recommendation available")
        }
    }
}
