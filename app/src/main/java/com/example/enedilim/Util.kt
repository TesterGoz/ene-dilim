package com.example.enedilim

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.enedilim.database.Letter
import org.json.JSONArray
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.concurrent.Executors

////Read Json
fun readJSONFromAsset(context: Context): JSONArray? {
    val json: String?
    try {
        val inputStream: InputStream = context.assets.open("tasks.json")
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return JSONArray(json)
}

///for the database thread
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

///get Today's date
@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): String {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
}

///get Today's time
@RequiresApi(Build.VERSION_CODES.O)
fun getTodayTime(): String {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
}


////format completedTasks
fun formatCompletedTasks(completedTasks: List<Letter>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        completedTasks.forEach {
            append("<br>")
            append(resources.getString(R.string.letter_name))
            append("\t${it.letterName}<br>")
            append(resources.getString(R.string.letter_word))
            append("\t${it.letterWord}<br>")
            append(resources.getString(R.string.completed_day))
            append("\t${it.taskCompletedDate}<br>")
            append(resources.getString(R.string.completed_time))
            append("\t${it.taskCompletedTime}<br>")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun formatCompletedTask(completedTask : Letter): String {
    val sb = StringBuilder()
    sb.apply {
            append("<br>")
            append("Harp: ")
            append("\t${completedTask.letterName}<br>")
            append("Söz: ")
            append("\t${completedTask.letterWord}<br>")
            append("Tamamlanan güni: ")
            append("\t${completedTask.taskCompletedDate}<br>")
            append("Tamamlanan wagty: ")
            append("\t${completedTask.taskCompletedTime}<br>")
        }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

