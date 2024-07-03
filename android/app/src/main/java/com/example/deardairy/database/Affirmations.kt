package com.example.deardairy.database

import android.content.Context
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//@Entity(tableName = "Affirmations")
@Entity
data class Affirmation(
    @PrimaryKey var id: Long = 0,
    @ColumnInfo(name = "text")var text: String?
)

@Dao
interface AffirmationDao {
    @Insert
    fun insert(vararg affirmation: Affirmation): LongArray

    @Insert
    fun insertAllAffirmations(affirmations: List<Affirmation>): LongArray

    @Query("SELECT * FROM Affirmation")
    fun getAll(): List<Affirmation>

    @Query("SELECT * FROM Affirmation WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Affirmation>

    @Query("SELECT * FROM Affirmation WHERE id = :id LIMIT 1")
    fun findById(id : Long): Affirmation?

//    @Query("SELECT COUNT(*) FROM Affirmation")
//    fun getCount(): Int

    @Query("SELECT COUNT(*) FROM Affirmation WHERE id = :id")
    fun getCount(id: Long): Int

    @Query("DELETE FROM Affirmation")
    fun deleteAll()
}

object DatabaseInitializer{

    private lateinit var database: AppDatabase
    lateinit var affirmationDao: AffirmationDao

    // Метод для инициализации базы данных
    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "dear_dairy_database"
        ).build()

        affirmationDao = database.affirmationDao()
    }

    // Метод для получения всех аффирмаций из списка
    private fun getAffirmationsList(): List<Affirmation> {
        val affirmations = mutableListOf<Affirmation>()
        affirmations.add(Affirmation(text = "I am worthy of love and respect."))
        affirmations.add(Affirmation(text = "I am capable of achieving my goals."))
        affirmations.add(Affirmation(text = "I am grateful for my journey."))
        affirmations.add(Affirmation(text = "I am growing stronger every day."))
        affirmations.add(Affirmation(text = "I am proud of my progress."))
        affirmations.add(Affirmation(text = "I trust my intuition."))
        affirmations.add(Affirmation(text = "I am confident in my abilities."))
        affirmations.add(Affirmation(text = "I embrace my uniqueness."))
        affirmations.add(Affirmation(text = "I am open to new opportunities."))
        affirmations.add(Affirmation(text = "I am surrounded by positive energy."))
        affirmations.add(Affirmation(text = "I choose to be happy."))
        affirmations.add(Affirmation(text = "I am at peace with myself."))
        affirmations.add(Affirmation(text = "I am resilient and can overcome challenges."))
        affirmations.add(Affirmation(text = "I am deserving of all good things."))
        affirmations.add(Affirmation(text = "I am filled with positive thoughts."))
        affirmations.add(Affirmation(text = "I am in control of my life."))
        affirmations.add(Affirmation(text = "I am grateful for today."))
        affirmations.add(Affirmation(text = "I believe in myself."))
        affirmations.add(Affirmation(text = "I am a positive thinker."))
        affirmations.add(Affirmation(text = "I am worthy of success."))
        affirmations.add(Affirmation(text = "I am in charge of my happiness."))
        affirmations.add(Affirmation(text = "I am a magnet for positivity."))
        affirmations.add(Affirmation(text = "I am becoming a better version of myself."))
        affirmations.add(Affirmation(text = "I am worthy of my dreams."))
        affirmations.add(Affirmation(text = "I am full of potential."))
        affirmations.add(Affirmation(text = "I am creating my own destiny."))
        affirmations.add(Affirmation(text = "I am grateful for my body."))
        affirmations.add(Affirmation(text = "I am a source of inspiration."))
        affirmations.add(Affirmation(text = "I am proud of who I am becoming."))
        affirmations.add(Affirmation(text = "I am open to change."))
        affirmations.add(Affirmation(text = "I am a beacon of light."))
        affirmations.add(Affirmation(text = "I am grateful for my health."))
        affirmations.add(Affirmation(text = "I am strong and courageous."))
        affirmations.add(Affirmation(text = "I am thankful for my support system."))
        affirmations.add(Affirmation(text = "I am free to be myself."))
        affirmations.add(Affirmation(text = "I am worthy of all the good in life."))
        affirmations.add(Affirmation(text = "I am grateful for my talents."))
        affirmations.add(Affirmation(text = "I am living my best life."))
        affirmations.add(Affirmation(text = "I am kind to myself and others."))
        affirmations.add(Affirmation(text = "I am in harmony with the universe."))
        affirmations.add(Affirmation(text = "I am a beautiful person."))
        affirmations.add(Affirmation(text = "I am open to abundance."))
        affirmations.add(Affirmation(text = "I am capable of great things."))
        affirmations.add(Affirmation(text = "I am enough just as I am."))
        affirmations.add(Affirmation(text = "I am grateful for my experiences."))
        affirmations.add(Affirmation(text = "I am committed to my personal growth."))
        affirmations.add(Affirmation(text = "I am filled with love and compassion."))
        affirmations.add(Affirmation(text = "I am worthy of inner peace."))
        affirmations.add(Affirmation(text = "I am grateful for my wisdom."))
        affirmations.add(Affirmation(text = "I am a work in progress."))
        affirmations.add(Affirmation(text = "I am strong in mind, body, and spirit."))
        affirmations.add(Affirmation(text = "I am a positive influence on others."))
        affirmations.add(Affirmation(text = "I am deserving of joy."))
        affirmations.add(Affirmation(text = "I am grateful for each moment."))
        affirmations.add(Affirmation(text = "I am in tune with my inner self."))
        affirmations.add(Affirmation(text = "I am worthy of my dreams and aspirations."))
        affirmations.add(Affirmation(text = "I am constantly learning and growing."))
        affirmations.add(Affirmation(text = "I am grateful for my resilience."))
        affirmations.add(Affirmation(text = "I am at peace with my past."))
        affirmations.add(Affirmation(text = "I am open to love and kindness."))
        affirmations.add(Affirmation(text = "I am a creator of my own destiny."))
        affirmations.add(Affirmation(text = "I am thankful for my blessings."))
        affirmations.add(Affirmation(text = "I am proud of my achievements."))
        affirmations.add(Affirmation(text = "I am a reflection of positivity."))
        affirmations.add(Affirmation(text = "I am worthy of a wonderful life."))
        affirmations.add(Affirmation(text = "I am grateful for the love in my life."))
        affirmations.add(Affirmation(text = "I am open to new possibilities."))
        affirmations.add(Affirmation(text = "I am deserving of a bright future."))
        affirmations.add(Affirmation(text = "I am in alignment with my purpose."))
        affirmations.add(Affirmation(text = "I am a positive thinker and achiever."))
        affirmations.add(Affirmation(text = "I am grateful for my courage."))
        affirmations.add(Affirmation(text = "I am embracing my true self."))
        affirmations.add(Affirmation(text = "I am worthy of great things."))
        affirmations.add(Affirmation(text = "I am at peace with where I am."))
        affirmations.add(Affirmation(text = "I am a source of happiness."))
        affirmations.add(Affirmation(text = "I am grateful for my journey so far."))
        affirmations.add(Affirmation(text = "I am open to positive changes."))
        affirmations.add(Affirmation(text = "I am surrounded by love."))
        affirmations.add(Affirmation(text = "I am grateful for today’s opportunities."))
        affirmations.add(Affirmation(text = "I am deserving of all my desires."))
        affirmations.add(Affirmation(text = "I am a beacon of positivity."))
        affirmations.add(Affirmation(text = "I am in control of my thoughts."))
        affirmations.add(Affirmation(text = "I am grateful for my inner strength."))
        affirmations.add(Affirmation(text = "I am capable of amazing things."))
        affirmations.add(Affirmation(text = "I am worthy of love and kindness."))
        affirmations.add(Affirmation(text = "I am open to new experiences."))
        affirmations.add(Affirmation(text = "I am thankful for my life."))
        affirmations.add(Affirmation(text = "I am growing every day."))
        affirmations.add(Affirmation(text = "I am deserving of all the good things in life."))
        affirmations.add(Affirmation(text = "I am grateful for my journey of self-discovery."))
        affirmations.add(Affirmation(text = "I am a positive force in the world."))
        affirmations.add(Affirmation(text = "I am worthy of all my accomplishments."))
        affirmations.add(Affirmation(text = "I am open to endless possibilities."))
        affirmations.add(Affirmation(text = "I am grateful for my unique journey."))
        affirmations.add(Affirmation(text = "I am a source of inspiration to others."))
        affirmations.add(Affirmation(text = "I am worthy of my dreams and goals."))
        affirmations.add(Affirmation(text = "I am embracing my journey with gratitude."))
        affirmations.add(Affirmation(text = "I am filled with positive energy."))
        affirmations.add(Affirmation(text = "I am thankful for my inner peace."))
        affirmations.add(Affirmation(text = "I am confident in my path forward."))

        var count = 0
        for (i in affirmations.indices) {
            affirmations[i].id = count.toLong()
            count += 1
        }
        Log.d("DatabaseInitializer", "Affirmations: $affirmations")
        return affirmations
    }

    // Метод для добавления всех аффирмаций в базу данных
    fun addAllAffirmations() {
        GlobalScope.launch {
            affirmationDao.deleteAll()

            val affirmations = getAffirmationsList()
            try {
                Log.d("DatabaseInitializer", "Inserting affirmations: $affirmations")
//                affirmationDao.insertAllAffirmations(affirmations)
                val insertedIds = affirmationDao.insertAllAffirmations(affirmations)

                Log.d("DatabaseInitializer", "Inserted affirmations successfully")
            } catch (e: Exception) {
                Log.e("DatabaseInitializer", "Error inserting affirmations", e)
            }
        }
    }
}