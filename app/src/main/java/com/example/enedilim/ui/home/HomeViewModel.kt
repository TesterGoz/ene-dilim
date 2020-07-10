package com.example.enedilim.ui.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.enedilim.database.Letter
import com.example.enedilim.database.LetterDatabaseDao
import com.example.enedilim.getTodayDate
import kotlinx.coroutines.*

class HomeViewModel(
    application: Application,
    private val dataSource: LetterDatabaseDao
) : AndroidViewModel(application) {

    private val alphabet = arrayOf(
        "Aa", "Bb", "Çç", "Dd", "Ee", "Ää", "Ff",
        "Gg", "Hh", "Ii", "Jj", "Žž", "Kk", "Ll", "Mm",
        "Nn", "Ññ", "Oo", "Öö", "Pp", "Rr", "Ss", "Şş", "Tt", "Uu", "Üü", "Ww", "Yy", "Ýý", "Zz"
    )

    //coroutine
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val database = dataSource


    //today
    @RequiresApi(Build.VERSION_CODES.O)
    val today = getTodayDate()

    val todayCompletedTasks = dataSource.getTodayCompletedTasks(today)

    ///newTask
    private var newLetterTask = MutableLiveData<Letter>()

    //navigations
    private val _navigateToTask = MutableLiveData<Letter>()
    val navigateToTask: LiveData<Letter> = _navigateToTask


    fun navigateTo(letterNumber: Int) {
        uiScope.launch {
            //todayCompletedLetterTask
            val todayCompletedLetterTask = getTodayTaskFromDatabase(letterNumber)

            //if we dont have any completed that letter today, get new letter task
            if(todayCompletedLetterTask == null){
                newLetterTask.value = getNewTaskFromDatabase(letterNumber)
                _navigateToTask.value = newLetterTask.value} else{
                newLetterTask.value = todayCompletedLetterTask
                _navigateToTask.value = newLetterTask.value
            }
        }

    }


    private suspend fun getTodayTaskFromDatabase(letterNumber: Int): Letter? {
        return withContext(Dispatchers.IO) {
            val task = dataSource.getTodayCompletedLetterTask(alphabet[letterNumber-1], today)
            task
        }
    }
    private suspend fun getNewTaskFromDatabase(letterNumber: Int): Letter? {
        return withContext(Dispatchers.IO) {
            val task = dataSource.getLetterTask(alphabet[letterNumber-1])
            task
        }
    }

    fun doneNavigation() {
        _navigateToTask.value = null
    }


    //coroutine ends
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
