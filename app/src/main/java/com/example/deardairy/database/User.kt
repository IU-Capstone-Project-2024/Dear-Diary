package com.example.deardairy.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.*


@Entity(tableName = "User")
data class User(
    var goals: String?,
    var notesCounter: Int,
    var emotionsCounter: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}