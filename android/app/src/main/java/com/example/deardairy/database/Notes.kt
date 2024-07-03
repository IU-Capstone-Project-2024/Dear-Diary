package com.example.deardairy.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

//@Entity(tableName = "Notes")
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var NoteId: Long = 0,
    var name: String = "My note",
    var text: String,
    var date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),  // Текущая дата
    var coverId: Long = (0..100).random().toLong()
)

@Dao
interface NoteDao {

    @Insert
    fun insert(vararg note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM Note WHERE NoteId = :NoteId")
    fun getNoteById(NoteId: Long): Note?

    @Query("SELECT COUNT(*) FROM Note")
    fun getNotesCount(): Int

    @Query("DELETE FROM Note")
    fun deleteAllNotes()
}