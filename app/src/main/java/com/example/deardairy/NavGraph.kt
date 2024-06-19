package com.example.deardairy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "loading_screen",
        modifier = modifier
    ) {
        composable("loading_screen") {
            LoadingScreen(onScreenTap = {navController.navigate("goals_screen") })
        }
        composable("goals_screen") {
            GoalsScreen(navController = navController)
        }
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable("new_emotion_input") {
            NewEmotionInputScreen(navController = navController)
        }
        composable("new_emotion") {
            NewEmotionScreen(navController = navController)
        }
        composable("my_emotions") {
            MyEmotionsScreen(navController = navController)
        }
        composable("new_note_screen") {
            NewNoteScreen(navController = navController)
        }
        composable("prev_note_screen") {
            PrevNoteScreen(navController = navController)
        }
        composable("my_emotions_before_analytics") {
            MyEmotionsBeforeAnalyticsScreen(navController)
        }
        composable("my_emotions_loading"){
            MyEmotionsLoading(navController = navController)
        }
    }
}
