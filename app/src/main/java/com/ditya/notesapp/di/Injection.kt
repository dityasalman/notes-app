package com.ditya.notesapp.di

import android.app.Application
import androidx.room.Room
import com.ditya.notesapp.db.dao.NotesDao
import com.ditya.notesapp.db.database.NotesDatabase
import com.ditya.notesapp.db.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbInjection {
    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase) = db.dao

    @Provides
    @Singleton
    fun provideNotesRepository(dao: NotesDao): NotesRepository {
        return NotesRepository(dao)
    }
}