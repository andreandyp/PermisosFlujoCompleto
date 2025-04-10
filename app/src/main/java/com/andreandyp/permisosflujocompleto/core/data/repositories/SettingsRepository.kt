package com.andreandyp.permisosflujocompleto.core.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andreandyp.permisosflujocompleto.core.domain.models.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private val photoPicker = booleanPreferencesKey("photoPicker")
    private val userName = stringPreferencesKey("userName")
    private val omitWelcome = booleanPreferencesKey("omitWelcome")

    val preferences: Flow<AppPreferences> = dataStore.data.map { preferences ->
        AppPreferences(
            photoPicker = preferences[photoPicker] ?: false,
            userName = preferences[userName] ?: "",
            omitWelcome = preferences[omitWelcome] ?: false,
        )
    }

    suspend fun savePhotoPickerConfig(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[photoPicker] = isEnabled
        }
    }

    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[userName] = name
        }
    }

    suspend fun saveOmitWelcome(omit: Boolean) {
        dataStore.edit { preferences ->
            preferences[omitWelcome] = omit
        }
    }
}