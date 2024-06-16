package com.example.deardairy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.deardairy.ui.theme.DearDairyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DearDairyTheme {
                // Поместите сюда ваши экраны

            }
        }
    }
}
