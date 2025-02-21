package com.ditya.notesapp.ui.main_screen

import com.ditya.notesapp.db.entity.NotesEntity

interface MainEvent {
    object addNote : MainEvent
    data class deleteNote(val id: Int) : MainEvent

}