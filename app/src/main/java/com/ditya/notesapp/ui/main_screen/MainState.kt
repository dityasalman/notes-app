package com.ditya.notesapp.ui.main_screen

import com.ditya.notesapp.db.entity.NotesEntity

data class MainState(
    val notes: List<NotesEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isSelecting: Boolean = false
)