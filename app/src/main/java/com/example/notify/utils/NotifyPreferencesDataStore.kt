package com.example.notify.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.notify.utils.Constants.FILE_NAME
import com.example.notify.utils.Constants.USER_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

class NotifyPreferencesDataStore (val context: Context) {


    private val TOKEN_KEY = stringPreferencesKey(USER_TOKEN)
    val tokenKeyFlow: Flow<String?> = context.dataStore.data.map {
        it[TOKEN_KEY]
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { userToken ->
            userToken[TOKEN_KEY] = token
        }
    }
}
