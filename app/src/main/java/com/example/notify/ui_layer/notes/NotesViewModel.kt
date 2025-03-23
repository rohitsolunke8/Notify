package com.example.notify.ui_layer.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.models.notes.NotesResponse
import com.example.notify.repo.NotesRepository
import com.example.notify.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(val notesRepo: NotesRepository): ViewModel() {

    private val _notesViewModel = MutableStateFlow(NetworkResult.Loading)
    val notesViewModel = _notesViewModel.asStateFlow()

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.getNotes()
        }
    }
}