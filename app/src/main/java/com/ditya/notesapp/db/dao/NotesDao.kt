package com.ditya.notesapp.db.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ditya.notesapp.db.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notesentity")
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM notesentity ORDER BY timestamp DESC")
    fun getAllNotesNewestFirst(): Flow<List<NotesEntity>>

    @Upsert
    fun upsertNote(note: NotesEntity)

    @Query("SELECT * FROM notesentity WHERE id = :id")
    fun getNoteById(id: Int): Flow<NotesEntity?>

    @Query("DELETE FROM notesentity WHERE id = :id")
    fun deleteNote(id: Int)

}