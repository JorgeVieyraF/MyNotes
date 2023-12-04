package com.lixoten.fido.feature_notes.domain.use_case

import com.lixoten.fido.feature_notes.data.NotesRepository
import com.lixoten.fido.feature_notes.model.Note

class GetNoteByIdUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Int): Note  {
        return repository.getNoteById(id)
    }
}
