package com.ditya.notesapp.ui.detail_screen

import com.ditya.notesapp.db.entity.NotesEntity

sealed interface DetailEvent {
    data class upsertNote(val note: NotesEntity) : DetailEvent
    data class deleteNote(val id: Int) : DetailEvent

}