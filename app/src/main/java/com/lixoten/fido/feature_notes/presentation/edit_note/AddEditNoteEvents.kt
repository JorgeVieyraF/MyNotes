package com.lixoten.fido.feature_notes.presentation.edit_note

import com.lixoten.fido.feature_notes.model.Note

sealed class AddEditNoteEvents {
    data class UpdateStateTitle(val value: String): AddEditNoteEvents()
    data class UpdateStateContent(val value: String): AddEditNoteEvents()
    data class UpdateStateColor(val value: Int): AddEditNoteEvents()
    data class UpdateDbNotes(val note: Note): AddEditNoteEvents()
    data class RemoveDbNote(val note: Note): AddEditNoteEvents()
    object RestoreDbNote: AddEditNoteEvents()
    object UpdateStateCheck: AddEditNoteEvents()
    object UpdateStatePinned: AddEditNoteEvents()
}
