package com.lixoten.fido.feature_notes.data

import com.lixoten.fido.feature_notes.model.Note
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun getNoteById(id: Int): Note {
        return notesDao.getNoteById(id)
    }

    override suspend fun getAllNotes(): List<Note> {
        return notesDao.getAllNotes()
    }

    override fun getAllNotesFlow(): Flow<List<Note>> {
        return notesDao.getAllNotesFlow()
    }

    override suspend fun insertNote(note: Note) {
        return notesDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return notesDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return notesDao.updateNote(note)
    }

    override suspend fun updateCompleted(id: Int, completed: Boolean) {
        return notesDao.updateCompleted(id, completed)
    }

}