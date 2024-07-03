package com.example.deardairy

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
//import com.example.deardairy.NoteViewModel  // Import your NoteViewModel here


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
//    noteViewModel: NoteViewModel
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
//        composable("new_emotion_input") {
//            NewEmotionInputScreen(navController = navController)
//        }
//        composable("new_emotion") {
//            NewEmotionScreen(navController = navController)
//        }
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
        composable("error_screen") {
            // Define your error screen here
        }
        composable("new_note_screen") {
//            NewNoteScreen(navController = navController, noteViewModel = noteViewModel)
            NewNoteScreen(navController = navController)
        }
//        composable("prev_note_screen") {
//            PrevNoteScreen(navController = navController, noteId = )
//        }
        composable("my_emotions_before_analytics") {
            MyEmotionsBeforeAnalyticsScreen(navController)
        }
//        composable(
//            "my_emotions_loading/{selectedTimePeriod}",
//            arguments = listOf(navArgument("selectedTimePeriod") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val selectedTimePeriod = backStackEntry.arguments?.getString("selectedTimePeriod") ?: "Today"
//            MyEmotionsLoading(navController = navController, selectedTimePeriod = selectedTimePeriod)
//        }
        composable(
            "my_emotions_analytics/{selectedTimePeriod}",
            arguments = listOf(navArgument("selectedTimePeriod") { type = NavType.StringType })
        ) { backStackEntry ->
            val selectedTimePeriod = backStackEntry.arguments?.getString("selectedTimePeriod") ?: "Today"
            MyEmotionsAnalytics(navController = navController, selectedTimePeriod = selectedTimePeriod)
        }
        composable(
            "prev_note_screen/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 0
            PrevNoteScreen(navController = navController, noteId = noteId)
        }

//        composable("new_emotion_input") { NewEmotionInputScreen(navController) }
        composable("new_emotion/{emotion}/{recommendation}") { backStackEntry ->
            val emotion = backStackEntry.arguments?.getString("emotion")
            val recommendation = backStackEntry.arguments?.getString("recommendation")
            NewEmotionScreen(navController, emotion ?: "Unknown Emotion", recommendation ?: "No recommendation available")
        }
    }
}
