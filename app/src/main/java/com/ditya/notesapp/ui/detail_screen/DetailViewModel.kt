package com.ditya.notesapp.ui.detail_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ditya.notesapp.db.entity.NotesEntity
import com.ditya.notesapp.db.repository.NotesRepository
import com.ditya.notesapp.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NotesRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

     private val noteId = savedStateHandle.toRoute<Routes.DetailScreen>().noteId

    private val _state = MutableStateFlow(DetailState())
    private val _note = repository.getNoteById(noteId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val state = combine(_state, _note){state, note ->
        state.copy(
            note = note
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DetailState())

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.upsertNote -> {
                upsertNote(event.note)
            }

            is DetailEvent.deleteNote -> {
                deleteNote(event.id)
            }
        }
    }

    private fun upsertNote(note: NotesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            note.timestamp = System.currentTimeMillis()
            repository.upsertNote(note)
        }
    }

    private fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(id)
        }
    }
}