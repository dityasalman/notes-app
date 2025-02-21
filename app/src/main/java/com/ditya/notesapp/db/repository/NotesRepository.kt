package com.ditya.notesapp.db.repository

import com.ditya.notesapp.db.dao.NotesDao
import com.ditya.notesapp.db.entity.NotesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository@Inject constructor(private val dao: NotesDao) {

    fun getAllNotes(): Flow<List<NotesEntity>> {
        return dao.getAllNotes()
    }

    fun upsertNote(note: NotesEntity) {
        dao.upsertNote(note)
    }

    fun getNoteById(id: Int): Flow<NotesEntity?> {
        return dao.getNoteById(id)
    }

    fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }

    fun getAllNotesNewestFirst(): Flow<List<NotesEntity>> {
        return dao.getAllNotesNewestFirst()
    }

}