package com.example.enedilim.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.enedilim.ioThread
import com.example.enedilim.readJSONFromAsset

@Database(entities = [Letter::class], version = 1, exportSchema = false)
abstract class LetterDatabase : RoomDatabase() {

    abstract val letterDatabaseDao: LetterDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: LetterDatabase? = null

        fun getInstance(context: Context): LetterDatabase {


            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = buildDatabase(context)
                    INSTANCE = instance
                }

                return instance
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LetterDatabase::class.java,
                "letters_details.db"
            )
                .addCallback(seedDatabaseCallback(context))
                .build()

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        val yourDao = getInstance(context).letterDatabaseDao
                        val tasksList = getTasksAsList(context)
                        yourDao.insertAllTasks(tasksList)
                    }
                }
            }
        }

        private fun getTasksAsList(context: Context): List<Letter> {

            val jsonArray = readJSONFromAsset(context)
            val tasksList = mutableListOf<Letter>()
            if (jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val newTask = Letter(
                        letterId = i,
                        letterName = jsonObject.getString("letterName"),
                        letterWord = jsonObject.getString("letterWord"),
                        letterWordImage = jsonObject.getString("letterWordImage"),
                        letterVoice = jsonObject.getString("letterVoice"),
                        letterWordVoice = jsonObject.getString("letterWordVoice"),
                        taskCompleted = 0,
                        taskCompletedDate = "",
                        taskCompletedTime = ""
                    )
                    tasksList.add(newTask)
                }
            }

            return tasksList.toList()
        }
    }
}