package ui.smartpro.firestorepuls.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun getId(): Long {
    val start = System.currentTimeMillis()
    val random = (1000..1000000).random()
    val id = (start / random)

    return id
}

@SuppressLint("SimpleDateFormat")
fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("HH.mm")
    return dateFormat.format(calendar.time)
}

fun asTime(): String {
    val time = Date()
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return timeFormat.format(time)
}

fun asTimeSchedule(): String {
    val time = Date()
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun asDate(): String {
    val time = Date()
    val timeFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return timeFormat.format(time)
}

fun asCustomDate(): String {
    val time = Date()
    val timeFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return timeFormat.format(time)
}

fun asCurrentDate(): String {
    val time = Date()
    val timeFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return timeFormat.format(time)
}