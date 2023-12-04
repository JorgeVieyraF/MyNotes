package com.lixoten.fido.di

import android.content.Context
import com.lixoten.fido.feature_notes.data.NotesDatabase
import com.lixoten.fido.feature_notes.data.NotesRepository
import com.lixoten.fido.feature_notes.data.OfflineNotesRepository
import com.lixoten.fido.feature_notes.domain.use_case.*

interface AppContainer {
    val notesRepository: NotesRepository
    val noteUseCases: NoteUseCases
}

/**
 * [AppContainer] implementation that provides instance of [OfflineNotesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     * Implementation for [NotesRepository]
     */
    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).notesDao())
    }

    override val noteUseCases: NoteUseCases by lazy {
        NoteUseCases(
            getAllNotes = GetAllNotesUseCase(repository = notesRepository),
            getNoteById = GetNoteByIdUseCase(repository = notesRepository),
            updateNote = UpdateNoteUseCase(repository = notesRepository),
            deleteNote = DeleteNoteUseCase(repository = notesRepository),
            addNote = AddNoteUseCase(repository = notesRepository),
            validateNote = ValidateNoteTitleUseCase(),
        )
    }
}