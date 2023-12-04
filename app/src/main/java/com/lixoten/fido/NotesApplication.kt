package com.lixoten.fido

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.lixoten.fido.di.AppContainer
import com.lixoten.fido.di.AppDataContainer
import com.lixoten.fido.feature_notes.data.UserPreferencesRepository

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class NotesApplication : Application() {
    lateinit var appContainer: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
