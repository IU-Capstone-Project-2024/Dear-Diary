package com.example.deardairy

import android.content.Context
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.deardairy.database.AppDatabase
//import com.example.deardairy.database.AppDatabase
import com.example.deardairy.ui.theme.BackgroundColor
import com.example.deardairy.ui.theme.BlueContainerColor
import com.example.deardairy.ui.theme.BodyTextStyle
import com.example.deardairy.ui.theme.CustomBoxWithTexts
import com.example.deardairy.ui.theme.DarkBlueColor
import com.example.deardairy.ui.theme.DisabledButtonColor
import com.example.deardairy.ui.theme.EmotionBox
import com.example.deardairy.ui.theme.LightBlueContainerColor
import com.example.deardairy.ui.theme.TitleTextStyle
import com.example.deardairy.ui.theme.TopBar
import com.example.deardairy.ui.theme.playfairDisplayFontFamily
import android.util.Log
import com.example.deardairy.database.Note
import com.example.deardairy.database.NoteDatabase
import com.example.deardairy.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DatabaseHelper {

    suspend fun getRandomAffirmation(context: Context): String {
        val database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "dear_dairy_database"
        ).build()

        val affirmationDao = database.affirmationDao()

        var randomId = (0..99).random()
        var randomIdLong = randomId.toLong()
        Log.d("AffirmationPreview", "Random ID: $randomId")

        val affirmation = affirmationDao.findById(randomIdLong)
        affirmation?.text ?: "No affirmation found"
        return affirmation?.text ?: "No affirmation found"
    }

    suspend fun getAllNotes(context: Context): List<Note> {
        val database = NoteDatabase.getDatabase(context)
        val noteDao = database.noteDao()

        return noteDao.getAllNotes()
    }
}


@Composable
fun MainScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val emotionsBlockHeight = screenHeightDp / 6.2f
    val scrollState = rememberScrollState()
    val cardWidth = screenWidthDp/2 - 25.dp - 10.5.dp
    val cardHeight =  cardWidth * 1.092f

    var randomAffirmation by remember { mutableStateOf("") }
    var NotesCounter by remember { mutableStateOf(0) }
    var notesList by remember { mutableStateOf<List<Note>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var userDatabase = UserDatabase.getDatabase(context)
            try{
                NotesCounter = userDatabase.userDao().getNotesCounter() ?: 0
                Log.d("MainScreen", "NotesCounter: ${NotesCounter}")
            } catch (e: Exception){
                Log.e("MainScreen", "Error getting notes counter", e)
            }
            try {
//                notesList = DatabaseHelper.getAllNotes(context)
                notesList = DatabaseHelper.getAllNotes(context).sortedByDescending { it.NoteId }

            } catch (e: Exception) {
                Log.e("MainScreen", "Error fetching notes", e)
            }
        }
        try {
            val affirmation = withContext(Dispatchers.IO) {
                DatabaseHelper.getRandomAffirmation(context)
            }
            randomAffirmation = affirmation
        } catch (e: Exception) {
            Log.e("MainScreen", "Error fetching affirmation", e)
            randomAffirmation = "Failed to load affirmation"
        }
    }

    Column(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // Верхняя часть
        TopBar(title = "Dear Diary", showLeftButton = false)

        // Emotions row, который также фиксирован
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(emotionsBlockHeight)
                .padding(horizontal = 21.dp)
                .padding(bottom = 17.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EmotionBox(
                modifier = Modifier
                    .clickable { navController.navigate("new_emotion_input") }
                    .width(screenWidthDp/3*2-25.dp- 10.5.dp),
                showAdditionalInfo = false,
                showButton = false,
                pictureWidth = emotionsBlockHeight - 30.dp - 12.dp,
                pictureHeight = emotionsBlockHeight - 30.dp,
                textWidth = (screenWidthDp/3*2-25.dp- 10.5.dp) - 24.dp - (emotionsBlockHeight - 30.dp - 12.dp)
            )

            Column(
                modifier = Modifier
                    .clickable { navController.navigate("my_emotions") }
                    .width(screenWidthDp / 3 - 25.dp - 10.5.dp)
                    .fillMaxSize()
                    .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 11.dp)
                    .padding(top = 19.dp, bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText(
                    text = "My recent emotions",
                    style = TextStyle(
                        fontFamily = playfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkBlueColor,
                        textAlign = TextAlign.Center
                    ),
                )
                Image(
                    painter = painterResource(id = R.drawable.my_emotions),
                    contentDescription = null,
                    modifier = Modifier
                        .width(emotionsBlockHeight * 0.36f)
                        .height(emotionsBlockHeight * 0.36f)
                )
            }
        }

        // Center
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
//                .height(600.dp)
                .padding(horizontal = 21.dp),
//                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    // new note
                    Column(
                        modifier = Modifier
                            .clickable { navController.navigate("new_note_screen") }
                            .width(screenWidthDp / 2 - 25.dp - 10.5.dp)
                            .height(cardHeight)
                            .background(
                                color = BlueContainerColor,
                                shape = RoundedCornerShape(24.dp)
                            )
//                            .padding(horizontal = 52.dp)
                            .padding(bottom = 11.dp)
                            .padding(top = 52.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
//                        contentAlignment = Alignment.Center
                    ) {
                        Box {
                            Icon(
                                painter = painterResource(id = R.drawable.plus), // ваше изображение
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                tint = Color.Unspecified
                            )
                        }

                        BasicText(
                            text = "Add new note", style = TextStyle(
                                fontFamily = playfairDisplayFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = BackgroundColor
                            )
                        )
                    }

                    if (notesList.isNotEmpty()) {
                        notesList.firstOrNull()?.let { firstNote ->
                            CustomBoxWithTexts(
                                navController = navController,
                                boxHeight = 60.dp,
                                text1 = firstNote.name,
                                text2 = "null",
                                text3 = firstNote.date ?: "No date"
                            )
                        }
                    }
                }
            }

            var nc = 2;
            var lastNote: Note? = null
            if (notesList.size > 1) {
                Log.d("MainScreen", "notelist: ${notesList.size}")
                val remainderNotes = notesList.drop(1).toMutableList()
                if ((remainderNotes.size) % 2 == 1){
                    Log.d("MainScreen", "odd: ${remainderNotes.size}")
                    lastNote = remainderNotes.removeLastOrNull()
                }

                Log.d("MainScreen", "remainderNotes: ${remainderNotes.size}")

                remainderNotes.chunked(2) { chunkedNotes ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            chunkedNotes.forEach { note ->
                                CustomBoxWithTexts(
                                    navController = navController,
                                    boxHeight = 60.dp,
                                    text1 = note.name,
                                    text2 = nc.toString(),
                                    text3 = note.date ?: "No date"
                                )
                                nc += 1
                            }

                        }
                    }
                }
                // Отобразить последнюю записку, если она была сохранена
                lastNote?.let { note ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomBoxWithTexts(
                                navController = navController,
                                boxHeight = 60.dp,
                                text1 = note.name,
                                text2 = nc.toString(),
                                text3 = note.date ?: "No date"
                            )
                        }
                    }
                }
            }
        }

        // Нижняя часть
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = 21.dp)
                .padding(vertical = 15.dp)
                .background(color = LightBlueContainerColor, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 15.dp)
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BasicText(
                text = randomAffirmation ?: "Loading...",
//                text = "test",
                style = TextStyle(
                    fontFamily = playfairDisplayFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = DarkBlueColor
                )
            )
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current
    MainScreen(navController = rememberNavController())
    var randomAffirmation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        randomAffirmation = DatabaseHelper.getRandomAffirmation(context)
    }
}
