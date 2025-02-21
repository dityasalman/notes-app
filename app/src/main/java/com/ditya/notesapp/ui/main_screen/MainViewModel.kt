package com.ditya.notesapp.ui.main_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditya.notesapp.db.entity.NotesEntity
import com.ditya.notesapp.db.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    private val _notes = repository.getAllNotesNewestFirst().stateIn(viewModelScope, SharingStarted.WhileSubscribed( ), emptyList())

    private val _state = MutableStateFlow(MainState())
    val state = combine(_state, _notes){state, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.addNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.upsertNote(
                        NotesEntity(
                            title = "Title",
                            content = "Content",
                        )
                    )
                }
            }
        }
    }

}