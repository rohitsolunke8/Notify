package com.example.notify.utils

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
    data object Ideal: NetworkResult<Nothing>()
}

sealed class NotesResult<out T> {
    data class Success<out T>(val data: T) : NotesResult<T>()
    data class Error(val message: String) : NotesResult<Nothing>()
    data object Loading : NotesResult<Nothing>()
    data object Ideal: NotesResult<Nothing>()
}