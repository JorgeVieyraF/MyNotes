package com.lixoten.fido.feature_notes.presentation.notes_list

import com.lixoten.fido.feature_notes.model.Note


sealed class NotesEvents{
    data class UpdateDbIsCheck(val note: Note): NotesEvents()
    data class UpdateDbIsUnpin(val note: Note): NotesEvents()
    data class RemoveDbRecord(val note: Note): NotesEvents()
    data class UpdateStateNoteOrderBy(val orderBy: NoteOrderBy): NotesEvents()
    object RestoreDbRecord: NotesEvents()
    object AddDbRecord: NotesEvents()
    object UpdateStateOrderSectionIsVisible: NotesEvents()
    object ToggleLayout: NotesEvents()
    object ToggleSearch: NotesEvents()
}