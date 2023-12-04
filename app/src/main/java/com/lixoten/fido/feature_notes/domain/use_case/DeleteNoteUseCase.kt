package com.lixoten.fido.feature_notes.domain.use_case

import com.lixoten.fido.feature_notes.data.NotesRepository
import com.lixoten.fido.feature_notes.model.Note

class DeleteNoteUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}
