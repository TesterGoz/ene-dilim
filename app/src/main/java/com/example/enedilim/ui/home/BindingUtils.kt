package com.example.enedilim.ui.home

import android.widget.Button
import android.widget.ImageView
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.enedilim.database.Letter
import com.example.enedilim.database.LetterDatabaseDao

@BindingAdapter("taskImage")
fun ImageView.setTaskImage(item: Letter?) {
    item?.let {
        if (item.letterWordImage != "")
            setImageResource(resources.getIdentifier(item.letterWordImage, "drawable", context.packageName ))
    }
}
@BindingAdapter("setTextColor")
fun setTextColor(view: Button, database: LetterDatabaseDao){
    val buttonName = view.text

}