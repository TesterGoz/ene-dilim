package com.example.enedilim.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface LetterDatabaseDao {

    @Insert
    fun insert(letter: Letter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTasks(letters: List<Letter?>?)

    @Update
    fun update(letter: Letter)

    @Query("SELECT * FROM letters_details WHERE letter_task_completed is 1 ORDER BY letter_task_completed_date DESC")
    fun getAllCompletedTasks(): LiveData<List<Letter>>


    @Query("SELECT * FROM letters_details WHERE letter_task_completed is 0 AND letter_name = :letterName LIMIT 1")
    fun getLetterTask(letterName: String): Letter?

    @Query("SELECT * from letters_details WHERE letterId = :key")
    fun getTasktWithId(key: Int): Letter?

    @Query("SELECT * from letters_details WHERE letter_task_completed_date = :todayDate")
    fun getTodayCompletedTasks(todayDate: String): LiveData<List<Letter>>

    @Query("SELECT * from letters_details WHERE letter_name = :letterName AND letter_task_completed_date = :todayDate LIMIT 1")
    fun getTodayCompletedLetterTask(letterName: String, todayDate: String): Letter?
}