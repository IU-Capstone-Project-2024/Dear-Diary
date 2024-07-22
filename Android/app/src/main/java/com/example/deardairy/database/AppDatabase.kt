package com.example.deardairy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Note ADD COLUMN date TEXT NOT NULL DEFAULT ''")
    }
}

object MigrationFrom1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Note ADD COLUMN coverId TEXT")
    }
}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                Log.d("UserDatabase", "Creating new database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                initializeUser(instance)
                return instance
            }
        }

        private fun initializeUser(database: UserDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = database.userDao()
                if (userDao.getUserById(0) == null) {
                    val user = User(userId = 0, goals = "", notesCounter = 0, emotionsCounter = 0)
                    userDao.insertUser(user)
                }
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}


@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                Log.d("NoteDatabase", "Creating new database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

@Database(entities = [Emotion::class], version = 1, exportSchema = false)
abstract class EmotionDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionDatabase? = null

        fun getDatabase(context: Context): EmotionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                Log.d("EmotionDatabase", "Creating new database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionDatabase::class.java,
                    "emotion_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

// User::class, Note::class, Emotion::class, , Note::class
@Database(entities = [Affirmation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun affirmationDao(): AffirmationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                Log.d("AppDatabase", "Creating new database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .addMigrations(MigrationFrom1To2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
