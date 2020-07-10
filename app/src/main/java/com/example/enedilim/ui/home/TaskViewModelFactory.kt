package com.example.enedilim.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enedilim.database.Letter
import com.example.enedilim.database.LetterDatabaseDao


//Provides the LetterDatabaseDao and context to the ViewModel.

class TaskViewModelFactory(
    val application: Application,
    private val dataSource: LetterDatabaseDao,
    private val newTask: Letter
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(application, dataSource, newTask) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}