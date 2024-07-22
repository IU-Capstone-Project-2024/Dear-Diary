package com.example.deardairy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.deardairy.database.AffirmationDao
import com.example.deardairy.database.AppDatabase
import com.example.deardairy.database.DatabaseInitializer
import com.example.deardairy.ui.theme.DearDairyTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseInitializer.initialize(this)

        GlobalScope.launch {
            DatabaseInitializer.addAllAffirmations()
        }

        setContent {
            DearDairyTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
fun MainPreview(){
}