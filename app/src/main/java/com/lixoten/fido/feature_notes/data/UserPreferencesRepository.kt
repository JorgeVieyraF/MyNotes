package com.lixoten.fido.feature_notes.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.lixoten.fido.feature_notes.data.UserPreferencesKeys.IS_GRID_LAYOUT
import kotlinx.coroutines.flow.*
import java.io.IOException

// Define your data class
data class UserPreferences(
    val isGridLayout: Boolean = false,
)

// Define your preference keys
object UserPreferencesKeys {
    val IS_GRID_LAYOUT = booleanPreferencesKey("is_grid_layout")
}

// Define a DataStore class to store and retrieve your data
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // Update the user preferences
    suspend fun updateUserPreferences(
        isGridLayout: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[IS_GRID_LAYOUT] = isGridLayout
        }
    }

    // Read the user preferences as a Flow
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // Handle the exception
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserPreferences(
                isGridLayout = preferences[IS_GRID_LAYOUT] ?: false,
            )
        }
}