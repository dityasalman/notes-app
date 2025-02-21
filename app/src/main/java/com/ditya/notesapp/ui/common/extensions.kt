package com.ditya.notesapp.ui.common

import android.util.Log
import com.ditya.notesapp.db.entity.NotesEntity
import com.ditya.notesapp.ui.detail_screen.DetailEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun Long.formatTimestamp(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val calendarNow = Calendar.getInstance()
    val calendarTimestamp = Calendar.getInstance()
    calendarTimestamp.timeInMillis = this // Ensure correct assignment

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "${diff / 1000} seconds ago" // Less than a minute
        diff < TimeUnit.HOURS.toMillis(1) -> "${diff / TimeUnit.MINUTES.toMillis(1)} minutes ago" // Less than an hour
        diff < TimeUnit.DAYS.toMillis(1) -> "${diff / TimeUnit.HOURS.toMillis(1)} hours ago" // Less than a day
        calendarNow.get(Calendar.YEAR) == calendarTimestamp.get(Calendar.YEAR) -> {
            when {
                calendarNow.get(Calendar.DAY_OF_YEAR) - calendarTimestamp.get(Calendar.DAY_OF_YEAR) == 1 -> "Yesterday" // Yesterday
                calendarNow.get(Calendar.WEEK_OF_YEAR) == calendarTimestamp.get(Calendar.WEEK_OF_YEAR) -> {
                    SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(this)) // Show day name (e.g., "Monday")
                }
                else -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(this)) // Show "13 Feb" for older dates in the same year
            }
        }
        else -> SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(Date(this)) // Show "Feb 2024" for different years
    }
}

// Setting how the data will show up in the main screen
fun previewFactory(note: NotesEntity): NotesEntity {
    if (note.title.isNotBlank()) return note
    else return note.copy(title = note.content.take(20), content = note.content.drop(20).take(100))
}

// Setting if the data will be deleted or updated
fun detailEventFactory(note: NotesEntity): DetailEvent {
    val updatedNote = note.copy(title = note.title, content = note.content, id = note.id)

    return if (note.title.isBlank() && note.content.isBlank()) {
        DetailEvent.deleteNote(note.id)
    }
    else DetailEvent.upsertNote(updatedNote)
}
