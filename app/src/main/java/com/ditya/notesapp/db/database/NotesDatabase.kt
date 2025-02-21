package com.ditya.notesapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ditya.notesapp.db.dao.NotesDao
import com.ditya.notesapp.db.entity.NotesEntity

@Database(
    entities = [NotesEntity::class],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {

    abstract val dao: NotesDao

}