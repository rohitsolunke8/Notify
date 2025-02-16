package com.example.notify.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    var loading: Boolean = false
) {
    class Success<T>(data: T, loading: Boolean) : NetworkResult<T>(data, loading = loading)

    class Error<T>(message: String?, data: T? = null, loading: Boolean) : NetworkResult<T>(data, message, loading = loading)

    class Loading<T>(loading : Boolean) : NetworkResult<T>(loading = loading)

    class Idel<T>: NetworkResult<T>()
}