package com.lixoten.fido.feature_notes.domain.use_case

import com.lixoten.fido.feature_notes.data.NotesRepository
import com.lixoten.fido.feature_notes.model.Note
import com.lixoten.fido.feature_notes.presentation.notes_list.NoteOrderBy
import com.lixoten.fido.feature_notes.presentation.notes_list.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetAllNotesUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(
        noteOrderBy: NoteOrderBy = NoteOrderBy.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getAllNotesFlow().map { notes ->
            when (noteOrderBy.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrderBy) {
                        is NoteOrderBy.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrderBy.Date -> notes.sortedBy { it.createdDate }
                        is NoteOrderBy.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrderBy) {
                        is NoteOrderBy.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrderBy.Date -> notes.sortedByDescending { it.createdDate }
                        is NoteOrderBy.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }

        }
    }
}