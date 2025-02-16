package com.example.notify.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.notify.utils.Constants.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object TokenManager {

    private const val TOKEN_MANAGER = TOKEN
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(TOKEN_MANAGER)

    internal suspend fun saveToken(
        value: String,
        key: Preferences.Key<String>,
        context: Context
    ) {
        context.dataStore.edit {
            it[key]
        }
    }

    internal fun getToken(
        key: Preferences.Key<String>,
        context: Context
    ): Flow<String?> = context.dataStore.data.map { it[key] }


}