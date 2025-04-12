package com.example.notify.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.notify.utils.Constants.FILE_NAME
import com.example.notify.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

class NotifyPreferencesDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context
    private val TOKEN_KEY = stringPreferencesKey(USER_TOKEN)

//    val newToken: Flow<String?> = appContext.dataStore.data.map {
//        it[TOKEN_KEY]
//    }

    fun getToken(): Flow<String?> {
        return appContext.dataStore.data.map {
            it[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        appContext.dataStore.edit { userToken ->
            userToken[TOKEN_KEY] = token
            Log.d("myToken", token)
        }
    }

    suspend fun clearToken() {
        appContext.dataStore.edit { userToken ->
            userToken.remove(TOKEN_KEY)
        }
    }
}
