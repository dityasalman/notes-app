package com.ditya.notesapp.ui.detail_screen

import com.ditya.notesapp.db.entity.NotesEntity

data class DetailState(
    val note: NotesEntity? = null,
    val isEmpty: Boolean = false,
    val isEditing: Boolean = false,
    val id: Int = 0,

)
