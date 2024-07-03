package com.example.deardairy.database

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.OnConflictStrategy


//@Entity(tableName = "User")
@Entity
data class User(
    @PrimaryKey
    var userId: Int = 0,
    var goals: String? = null,
    var notesCounter: Int = 0,
    var emotionsCounter: Int = 0
)

@Dao
interface UserDao {
    @Query("DELETE FROM User")
    fun deleteAllUsers()

    @Query("UPDATE User SET goals = :goals WHERE userId = 0 ")
    fun updateGoals(goals: String?)

    @Query("UPDATE User SET notesCounter = :trueCount WHERE userId = 0 ")
    fun updateNotesCounter(trueCount: Int)

    @Query("UPDATE User SET emotionsCounter = :trueCount WHERE userId = 0 ")
    fun updateEmotionsCounter(trueCount: Int)

    @Query("SELECT notesCounter FROM User WHERE userId = 0")
    fun getNotesCounter(): Int?

    @Query("SELECT emotionsCounter FROM User WHERE userId = 0")
    fun getEmotionsCounter(): Int?

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserById(userId: Int): User?

//    @Insert
//    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}

fun initializeUser(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val userDatabase = UserDatabase.getDatabase(context)
        val userDao = userDatabase.userDao()

        val user = userDao.getUserById(0)
        if (user == null) {
            val newUser = User(
                userId = 0,
                goals = null,
                notesCounter = 0,
                emotionsCounter = 0
            )
            userDao.insertUser(newUser)
            Log.d("UserDatabase", "User with userId = 0 created")
        } else {
            Log.d("UserDatabase", "User with userId = 0 already exists")
        }
    }
}