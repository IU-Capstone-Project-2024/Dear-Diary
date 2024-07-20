package com.example.deardairy.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class Emotion(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var text: String,
    var date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
)

@Dao
interface EmotionDao {
    @Insert
    fun insertEmotion(emotion: Emotion)

    @Query("SELECT * FROM Emotion")
    fun getAllEmotions(): List<Emotion>

    @Query("SELECT * FROM Emotion WHERE id = :id")
    fun getEmotionById(id: Long): Emotion?

    @Query("SELECT COUNT(*) FROM Emotion")
    fun getEmotionCount(): Int

    @Query("SELECT * FROM emotion WHERE date BETWEEN :startDate AND :endDate")
    fun getEmotionsBetweenDates(startDate: String, endDate: String): List<Emotion>
}