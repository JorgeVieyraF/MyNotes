package com.lixoten.fido.feature_notes.presentation.notes_list

import com.lixoten.fido.feature_notes.model.Note

data class NotesUiState(
    val noteOrderBy: NoteOrderBy = NoteOrderBy.Title(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSearchVisible: Boolean = false,
    val isGridLayout: Boolean = false,
    val notes: List<Note> = emptyList()
)

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

sealed class NoteOrderBy(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrderBy(orderType)
    class Date(orderType: OrderType): NoteOrderBy(orderType)
    class Color(orderType: OrderType): NoteOrderBy(orderType)
    fun copy(orderType: OrderType): NoteOrderBy {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}