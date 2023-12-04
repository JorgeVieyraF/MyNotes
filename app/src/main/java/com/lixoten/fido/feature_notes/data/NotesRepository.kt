package com.lixoten.fido.feature_notes.data

import com.lixoten.fido.feature_notes.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getNoteById(id: Int): Note

    suspend fun getAllNotes(): List<Note>

    fun getAllNotesFlow(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun updateCompleted(id: Int, completed: Boolean)
}
