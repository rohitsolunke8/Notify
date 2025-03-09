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

val tokenManger = stringPreferencesKey(USER_TOKEN)
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(FILE_NAME)

suspend fun saveToken(context: Context, token: String) {
    context.dataStore.edit { preferences ->
        preferences[tokenManger] = token
    }
}

fun getToken(context: Context): Flow<String?> {
    return context.dataStore.data.map { preferences ->
        preferences[tokenManger]
    }
}