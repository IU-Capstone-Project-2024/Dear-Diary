package com.example.deardairy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.ui.theme.DearDairyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DearDairyTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
