package com.example.enedilim.ui.dashboard

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.enedilim.R
import com.example.enedilim.database.Letter


@BindingAdapter("completedTaskImage")
fun ImageView.setCompletedTaskImage(item: Letter?) {
    item?.let {
        setImageResource(resources.getIdentifier(item.letterWordImage,"drawable", context.packageName))
    }
}

@BindingAdapter("completedTaskLetterName")
fun TextView.setCompletedTaskLetterName(item: Letter?) {
    item?.let {
        text = context.getString(R.string.letter) + item.letterName
    }
}

@BindingAdapter("completedTaskWord")
fun TextView.setCompletedTaskWord(item: Letter?) {
    item?.let {
        text = context.getString(R.string.word) + item.letterWord
    }
}

@BindingAdapter("completedTaskDate")
fun TextView.setCompletedTaskDate(item: Letter?) {
    item?.let {
        text = context.getString(R.string.date) + item.taskCompletedDate
    }
}

@BindingAdapter("completedTaskTime")
fun TextView.setCompletedTaskTime(item: Letter?) {
    item?.let {
        text = context.getString(R.string.time) + item.taskCompletedTime
    }
}