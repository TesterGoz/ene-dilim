package com.example.enedilim.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "letters_details")
data class Letter(
    @PrimaryKey(autoGenerate = false)
    var letterId: Int = 0,

    @ColumnInfo(name = "letter_name")
    var letterName: String = "",

    @ColumnInfo(name = "letter_word")
    var letterWord: String = "",

    @ColumnInfo(name = "letter_word_image")
    var letterWordImage: String,

    @ColumnInfo(name = "letter_voice")
    var letterVoice: String = "",

    @ColumnInfo(name = "letter_word_voice")
    var letterWordVoice: String = "",

    @ColumnInfo(name = "letter_task_completed")
    var taskCompleted: Int = 0,

    @ColumnInfo(name = "letter_task_completed_date")
    var taskCompletedDate: String = "",

    @ColumnInfo(name = "letter_task_completed_time")
    var taskCompletedTime: String = ""
) : Parcelable