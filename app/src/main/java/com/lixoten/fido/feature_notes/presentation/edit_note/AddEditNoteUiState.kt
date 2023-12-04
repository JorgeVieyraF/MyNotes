package com.lixoten.fido.feature_notes.presentation.edit_note

import com.lixoten.fido.feature_notes.model.Note

data class AddEditNoteUiState(
    val note: Note = Note(-1,"","",0,false, false),
    val foo: String = "",
    val titleError: Boolean = false,
    val dataHasChanged: Boolean = false,
)
