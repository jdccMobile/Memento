package com.jdccmobile.memento.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jdccmobile.memento.ui.views.SplashActivity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES = "my_preferences"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES) // con el by generamos un singleton -> unica instancia de la base de datos

class DataStoreRepositoryImp @Inject constructor(
    private val context: Context
): DataStoreRepository {


    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[preferencesKey]
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[preferencesKey]
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

}