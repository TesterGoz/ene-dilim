package com.example.enedilim.ui.home

import android.app.Application
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.enedilim.database.Letter
import com.example.enedilim.database.LetterDatabaseDao
import com.example.enedilim.getTodayDate
import com.example.enedilim.getTodayTime
import kotlinx.coroutines.*

class TaskViewModel(
    application: Application,
    dataSource: LetterDatabaseDao,
    newTask: Letter
) : AndroidViewModel(application) {

    val database = dataSource

    //coroutine
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //todayDate an time
    @RequiresApi(Build.VERSION_CODES.O)
    val todayDate = getTodayDate()
    @RequiresApi(Build.VERSION_CODES.O)
    val todayTime = getTodayTime()

    var newLetterTask = newTask


    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?> = _navigateToHome


    ///taskLetterVoice clicked
    fun taskLetterVoiceClicked(newTask: Letter){
        if (newTask.letterVoice != ""){
           val resId = getApplication<Application>().resources.getIdentifier(newTask.letterVoice, "raw", getApplication<Application>().packageName )
           val mediaPlayer = MediaPlayer.create(getApplication<Application>().applicationContext, resId )
            mediaPlayer.start()
        }

    }

    ///taskWordVoice clicked
    fun taskWordVoiceClicked(newTask: Letter){
        if (newTask.letterWordVoice != ""){
            val resId = getApplication<Application>().resources.getIdentifier(newTask.letterWordVoice, "raw", getApplication<Application>().packageName )
            val mediaPlayer = MediaPlayer.create(getApplication<Application>().applicationContext, resId )
            mediaPlayer.start()
        }
    }

    ////taskCompleteClicked
    private val _taskCompleteClicked = MutableLiveData<Boolean>()
    val taskCompleteClicked: LiveData<Boolean> = _taskCompleteClicked


    fun taskCompleteClicked(){

        uiScope.launch {

            val oldLetterTask = newLetterTask

            oldLetterTask.taskCompletedDate = todayDate
            oldLetterTask.taskCompletedTime = todayTime
            oldLetterTask.taskCompleted = 1

            update(oldLetterTask)

            _taskCompleteClicked.value = true
            newLetterTask = oldLetterTask
        }
    }

    private suspend fun update(newTask: Letter) {
        withContext(Dispatchers.IO) {
            database.update(newTask)
        }
    }



    private val _submitButtonVisible = MutableLiveData<Boolean>()
    val submitButtonVisible: LiveData<Boolean> = _submitButtonVisible
    fun makeSubmitButtonNotVisible(){
        _submitButtonVisible.value = false
    }
    fun makeSubmitButtonVisible(){
        _submitButtonVisible.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating() {
        _navigateToHome.value = null
    }

    fun onClose() {
        _navigateToHome.value = true
    }

}