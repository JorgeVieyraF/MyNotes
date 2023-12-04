package com.lixoten.fido.feature_notes.presentation.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lixoten.fido.NotesApplication
import com.lixoten.fido.feature_notes.data.UserPreferencesRepository
import com.lixoten.fido.feature_notes.domain.use_case.NoteUseCases
import com.lixoten.fido.feature_notes.model.Note
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewmodel(
    private val noteUseCasesWrapper: NoteUseCases,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState = _uiState.asStateFlow()
    private var getNotesJob: Job? = null
    private var deletedNote: Note? = null
    private val x = 2
    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect {
                _uiState.value = uiState.value.copy(
                    isGridLayout = it.isGridLayout,
                )
            }
        }
        if (x == 1) {
        } else {
            getAllDbNotes(
                noteOrderBy = NoteOrderBy.Title(OrderType.Ascending)
            )
        }

    }

    private fun getAllDbNotes(noteOrderBy: NoteOrderBy) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCasesWrapper.getAllNotes(noteOrderBy)
            .onEach { notes ->
                _uiState.update {
                    uiState.value.copy(
                        notes = notes,
                        noteOrderBy = noteOrderBy
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun updatePreferenceLayout(newValue: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferences(isGridLayout = newValue)
        }
    }

    fun onEvents(event: NotesEvents) {
        when (event) {
            is NotesEvents.ToggleLayout -> {
                _uiState.update {
                    uiState.value.copy(
                        isGridLayout = !uiState.value.isGridLayout
                    )
                }
                updatePreferenceLayout(uiState.value.isGridLayout)
            }
            is NotesEvents.RemoveDbRecord -> {
                viewModelScope.launch {
                    deletedNote = event.note

                    noteUseCasesWrapper.deleteNote(event.note)
                }
            }
            is NotesEvents.RestoreDbRecord -> {
                viewModelScope.launch {
                    noteUseCasesWrapper.addNote(deletedNote ?: return@launch)
                    deletedNote = null
                }
            }
            is NotesEvents.AddDbRecord -> {
                viewModelScope.launch {
                val sz = uiState.value.notes.count()
                    noteUseCasesWrapper.addNote(
                        Note(
                            id = -1,
                            title = "",
                            content = "",
                            color = 0,
                            isPinned = false,
                        ),
                        cnt = sz
                    )
                }
            }
            is NotesEvents.UpdateStateOrderSectionIsVisible -> {
                _uiState.update {
                    uiState.value.copy(
                        isOrderSectionVisible = !uiState.value.isOrderSectionVisible
                    )
                }
            }
            is NotesEvents.ToggleSearch -> {
                _uiState.update {
                    uiState.value.copy(
                        isSearchVisible = !uiState.value.isSearchVisible
                    )
                }
            }

            is NotesEvents.UpdateDbIsCheck -> {
                viewModelScope.launch {
                    noteUseCasesWrapper.updateNote(
                        note = event.note.copy(
                            isChecked = !event.note.isChecked
                        )
                    )
                }
            }
            is NotesEvents.UpdateDbIsUnpin -> {
                viewModelScope.launch {
                    noteUseCasesWrapper.updateNote(
                        note = event.note.copy(
                            isPinned = !event.note.isPinned
                        )
                    )
                }
            }
            is NotesEvents.UpdateStateNoteOrderBy -> {
                _uiState.update {
                    uiState.value.copy(
                        noteOrderBy = event.orderBy.copy(orderType = event.orderBy.orderType)
                    )
                }
                getAllDbNotes(event.orderBy)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)
                val noteUseCasesXXX = application.appContainer.noteUseCases
                val preferencesRepository = application.userPreferencesRepository
            NotesViewmodel(
                    noteUseCasesWrapper = noteUseCasesXXX,
                    userPreferencesRepository = preferencesRepository
                )
            }
        }
    }
}
