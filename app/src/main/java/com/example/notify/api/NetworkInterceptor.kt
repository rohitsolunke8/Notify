package com.example.notify.api

import android.content.Context
import com.example.notify.utils.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private var tokenManager: TokenManager,
    @ApplicationContext val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
        val token = tokenManager.getToken(context)
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())

    }
}