package com.example.notify.api

import android.content.Context
import android.util.Log
import com.example.notify.utils.NotifyPreferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {

    var token = CoroutineScope(Dispatchers.IO).launch {
        NotifyPreferencesDataStore(context).getToken().first()
    }

//    var token = NotifyPreferencesDataStore(context).newToken
//    var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJvaGl0c29sdW5rZUBnbWFpbC5jb20iLCJpZCI6IjY3ZTU2YmFkNzlkNmE5YzkwZDQzZGViMiIsImlhdCI6MTc0NDM3MTEyNH0.SwD5uX5a58ul6jmtNsD9vepIekGvjJgMRBTLPiCst0c"

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("interceptorToken" , "${token}")
        val request = chain
            .request()
            .newBuilder()
        request.addHeader("Authorization", "Bearer$token")
        return chain.proceed(request.build())
    }
}