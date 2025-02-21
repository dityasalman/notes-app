package com.ditya.notesapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object MainScreen : Routes

    @Serializable
    data class DetailScreen(val noteId: Int) : Routes
}