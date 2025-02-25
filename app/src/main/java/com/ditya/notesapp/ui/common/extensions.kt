package com.ditya.notesapp.ui.common

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
    calendarTimestamp.timeInMillis = this

    val dateFormatDayName = SimpleDateFormat("EEEE", Locale.getDefault()) // "Monday"
    val dateFormatDayMonth = SimpleDateFormat("dd MMM", Locale.getDefault()) // "13 Feb"
    val dateFormatMonthYear = SimpleDateFormat("MMM yyyy", Locale.getDefault()) // "Feb 2024"

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "${diff / 1000} seconds ago"
        diff < TimeUnit.HOURS.toMillis(1) -> "${diff / TimeUnit.MINUTES.toMillis(1)} minutes ago"
        diff < TimeUnit.DAYS.toMillis(1) -> "${diff / TimeUnit.HOURS.toMillis(1)} hours ago"
        calendarNow.get(Calendar.DAY_OF_YEAR) - calendarTimestamp.get(Calendar.DAY_OF_YEAR) == 1 &&
                calendarNow.get(Calendar.YEAR) == calendarTimestamp.get(Calendar.YEAR) -> "Yesterday"
        diff < TimeUnit.DAYS.toMillis(7) -> dateFormatDayName.format(Date(this)) // Show day name if within 7 days
        calendarNow.get(Calendar.YEAR) == calendarTimestamp.get(Calendar.YEAR) -> dateFormatDayMonth.format(Date(this)) // Same year, show "13 Feb"
        else -> dateFormatMonthYear.format(Date(this)) // Different year, show "Feb 2024"
    }
}

// Setting how the data will show up in the main screen
fun previewFactory(note: NotesEntity): NotesEntity {
    return if (note.title.isNotBlank()) note
    else note.copy(title = note.content.take(20), content = note.content.drop(20).take(100))
}

// Setting if the data will be deleted or updated
fun detailEventFactory(note: NotesEntity): DetailEvent {
    val updatedNote = note.copy(title = note.title, content = note.content, id = note.id)

    return if (note.title.isBlank() && note.content.isBlank()) {
        DetailEvent.deleteNote(note.id)
    }
    else DetailEvent.upsertNote(updatedNote)
}