package com.example.notify.repo

import android.R.id.message
import com.example.notify.api.NotesApi
import com.example.notify.models.notes.NotesRequest
import com.example.notify.models.notes.NotesResponse
import com.example.notify.utils.NotesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesApi: NotesApi) {

    fun getNotes(): Flow<NotesResult<Response<List<NotesResponse>>>> = flow {

        try {
            val notesResponse = notesApi.getNotes()
            if (notesResponse.isSuccessful && notesResponse.body() != null) {
                emit(NotesResult.Success(notesResponse))
            } else if (notesResponse.errorBody() != null) {
                val errorBody = JSONObject(notesResponse.errorBody()!!.charStream().readText())
                emit(NotesResult.Error(errorBody.toString()))
            } else if (notesResponse.message() != null) {
                emit(NotesResult.Error(message.toString()))
            }
        } catch (e: Exception) {
            emit(NotesResult.Error(e.message.toString()))
        }
    }

    fun addNote(newNote: NotesRequest): Flow<NotesResult<Response<NotesResponse>>> = flow {
        val notesResponse = notesApi.addNote(newNote)
        try {
            NotesResult.Loading
            if (notesResponse.isSuccessful && notesResponse.body() != null) {
                emit(NotesResult.Success(notesResponse))
            } else if (notesResponse.errorBody() != null) {
                val errorBody = JSONObject(notesResponse.errorBody()!!.charStream().readText())
                emit(NotesResult.Error(errorBody.toString()))
            }
        } catch (e: Exception) {
            emit(NotesResult.Error(e.message.toString()))
        }
    }

    fun updateNote(
        noteId: String,
        updateNoteRequest: NotesRequest
    ): Flow<NotesResult<Response<NotesResponse>>> = flow {
        val response = notesApi.updateNote(noteId = noteId, noteRequest = updateNoteRequest)
        try {
            NotesResult.Loading
            if (response.isSuccessful && response.body() != response.body()) {
                emit(NotesResult.Success(response))
            } else if (response.errorBody() != null) {
                val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
                emit(NotesResult.Error(errorBody.toString()))
            }
        } catch (e: Exception) {
            emit(NotesResult.Error(e.message.toString()))
        }
    }

    fun deleteNote(noteId: String): Flow<NotesResult<Response<NotesResponse>>> = flow {
        val response = notesApi.deleteNote(noteId)
        try {
            NotesResult.Loading
            if (response.isSuccessful && response.body() != response.body()) {
                emit(NotesResult.Success(response))
            } else if (response.errorBody() != null) {
                val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
                emit(NotesResult.Error(errorBody.toString()))
            }
        } catch (e: Exception) {
            emit(NotesResult.Error(e.message.toString()))
        }
    }
}