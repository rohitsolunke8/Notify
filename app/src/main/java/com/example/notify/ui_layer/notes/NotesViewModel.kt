package com.example.notify.ui_layer.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.models.notes.NotesRequest
import com.example.notify.models.notes.NotesResponse
import com.example.notify.repo.NotesRepository
import com.example.notify.utils.NotesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepo: NotesRepository) : ViewModel() {

    private var _notesViewModel =
        MutableStateFlow<NotesResult<Response<List<NotesResponse>>>>(NotesResult.Ideal)
    val notesViewModel: StateFlow<NotesResult<Response<List<NotesResponse>>>> =
        _notesViewModel.asStateFlow()

    private fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.getNotes().collect { notes ->
                when (notes) {
                    is NotesResult.Error -> {
                        _notesViewModel.update {
                            NotesResult.Error(notes.message)
                        }
                    }

                    is NotesResult.Loading -> {
                        _notesViewModel.update {
                            NotesResult.Loading
                        }
                    }

                    is NotesResult.Success<Response<List<NotesResponse>>> -> {
                        _notesViewModel.update {
                            NotesResult.Success<Response<List<NotesResponse>>>(notes.data)
                        }
                    }

                    is NotesResult.Ideal -> {
                        _notesViewModel.update {
                            NotesResult.Ideal
                        }
                    }
                }
                Log.d("$notes", "NotesResponse")
            }
        }
    }

    fun addNote(newNote: NotesRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.addNote(newNote = newNote)
        }
    }

    init {
        getAllNotes()
    }
}