package com.example.enedilim.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.enedilim.database.LetterDatabaseDao
import com.example.enedilim.formatCompletedTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DashboardViewModel(
    application: Application,
    dataSource: LetterDatabaseDao
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val completedTasks = dataSource.getAllCompletedTasks()

    val completedTasksString = Transformations.map(completedTasks) {completedTasks ->
        formatCompletedTasks(completedTasks, application.resources)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}