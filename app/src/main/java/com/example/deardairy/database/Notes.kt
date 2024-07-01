package com.example.deardairy.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
//    var date: Date,
    var text: String,
    var coverId: Long
)