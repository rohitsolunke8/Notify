package com.example.notify.api

import com.example.notify.models.notes.NotesRequest
import com.example.notify.models.notes.NotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {

    @GET("api/notes/")
    suspend fun getNotes(): Response<List<NotesResponse>>

    @POST("api/notes/")
    suspend fun addNote(@Body noteRequest: NotesRequest): Response<NotesResponse>

    @PUT("api/notes/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body noteRequest: NotesRequest) : Response<NotesResponse>

    @DELETE("api/notes/{noteId}")
    suspend fun deleteNote(@Path("notedId") noteId: String,@Body noteRequest: NotesRequest) : Response<NotesResponse>

}