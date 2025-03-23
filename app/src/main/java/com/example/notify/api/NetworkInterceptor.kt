package com.example.notify.api

import android.content.Context
import com.example.notify.utils.NotifyPreferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {

    val tokenManager = NotifyPreferencesDataStore(context)

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
        val token = tokenManager.tokenKeyFlow
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())

    }
}