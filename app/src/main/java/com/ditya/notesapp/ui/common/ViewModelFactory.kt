package com.ditya.notesapp.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ditya.notesapp.db.repository.NotesRepository
import com.ditya.notesapp.ui.main_screen.MainViewModel

class ViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java ->  MainViewModel(repository) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    }

}