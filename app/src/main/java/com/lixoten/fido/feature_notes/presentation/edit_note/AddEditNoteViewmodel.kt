package com.lixoten.fido.feature_notes.presentation.edit_note

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lixoten.fido.NotesApplication
import com.lixoten.fido.feature_notes.domain.use_case.NoteUseCases
import com.lixoten.fido.feature_notes.model.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddEditNoteViewmodel(
    savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState = _uiState.asStateFlow()
    private var deletedNote: Note? = null
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { idArgument ->
            if (idArgument != -1) {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            note = noteUseCases.getNoteById(idArgument),
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditNoteEvents) {
        when (event) {
            is AddEditNoteEvents.UpdateStateTitle -> {
                _uiState.update {
                    it.copy(
                        note = _uiState.value.note.copy(
                            title = event.value,
                        ),
                        dataHasChanged = true,
                        titleError = (
                             !noteUseCases.validateNote.execute(event.value).successful
                        )
                    )
                }
            }
            is AddEditNoteEvents.UpdateStateContent -> {
                _uiState.update {
                    it.copy(
                        note = _uiState.value.note.copy(
                            content = event.value,
                        ),
                        dataHasChanged = true,
                    )
                }
            }
            is AddEditNoteEvents.UpdateStateColor -> {
                _uiState.update {
                    it.copy(
                        note = _uiState.value.note.copy(
                            color = event.value,
                        ),
                        dataHasChanged = true,
                    )
                }
            }
            is AddEditNoteEvents.UpdateStateCheck -> {
                _uiState.update {
                    it.copy(
                        note = _uiState.value.note.copy(
                            isChecked = !uiState.value.note.isChecked,
                        ),
                        dataHasChanged = true,
                    )
                }
            }
            is AddEditNoteEvents.UpdateStatePinned -> {
                _uiState.update {
                    it.copy(
                        note = _uiState.value.note.copy(
                            isPinned = !uiState.value.note.isPinned,
                        ),
                        dataHasChanged = true,
                    )
                }
            }
            is AddEditNoteEvents.UpdateDbNotes -> {
                viewModelScope.launch {
                    noteUseCases.addNote(
                        uiState.value.note
                    )
                }
            }
            is AddEditNoteEvents.RemoveDbNote -> {
                viewModelScope.launch {
                    deletedNote = event.note

                    noteUseCases.deleteNote(event.note)
                }
            }
            is AddEditNoteEvents.RestoreDbNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(deletedNote ?: return@launch)
                    deletedNote = null
                }
            }
        }
    }


    // Notes: Question: At moment this is chuck of code is repeated in two files
    //  in QueryViewModel and in DetailsViewModel.
    //  what can I do/ place it so as not to have repeat code? I tried but I got a bunch of errors
    /**
     * Factory for BookshelfViewModel] that takes BookshelfRepository] as a dependency
     */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)
                //val notesRepository = application.appContainer.notesRepository
                val noteUseCasesXXX = application.appContainer.noteUseCases
                AddEditNoteViewmodel(
                    this.createSavedStateHandle(),
                    //notesRepository = notesRepository,
                    noteUseCases = noteUseCasesXXX
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        //object SaveNote: UiEvent()
    }

}
