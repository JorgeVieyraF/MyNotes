package com.lixoten.fido.feature_notes.domain.use_case

// variable for every use_case we have
data class NoteUseCases(
    // after adding here go to appMolule in "di" and add there too
   val getAllNotes: GetAllNotesUseCase,
   val getNoteById: GetNoteByIdUseCase,
   val updateNote: UpdateNoteUseCase,
   val deleteNote: DeleteNoteUseCase,
   val addNote: AddNoteUseCase,
   val validateNote: ValidateNoteTitleUseCase,
)
