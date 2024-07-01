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
import com.example.deardairy.database.DatabaseInitializer
import com.example.deardairy.ui.theme.DearDairyTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // Инициализация базы данных
        DatabaseInitializer.initialize(this)


        // Добавление аффирмаций в базу данных
        GlobalScope.launch {
            DatabaseInitializer.addAllAffirmations()
////            val count = DatabaseInitializer.affirmationDao.getCount()
////            Log.d("AffirmationCount", "Количество рядов в таблице Affirmation: $count")
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
    val count = 5
    println("Количество рядов в таблице Affirmation: $count")
}