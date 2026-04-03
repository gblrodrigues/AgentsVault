package com.gblrod.agentsvault.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gblrod.agentsvault.components.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val toggleFavorite = stringSetPreferencesKey(name = "toggle_favorite")
private val themeKey = stringPreferencesKey(name = "theme")

class PrefsDataStore(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun toggleFavorite(idAgent: String) {
        dataStore.edit { prefs ->
            val currentFavorites = prefs[toggleFavorite] ?: emptySet()

            prefs[toggleFavorite] =
                if (currentFavorites.contains(idAgent)) {
                    currentFavorites - idAgent
                } else {
                    currentFavorites + idAgent
                }
        }
    }

    val agentFavoriteFlow: Flow<Set<String>> =
        dataStore.data
            .map { prefs ->
                prefs[toggleFavorite] ?: emptySet()
            }

    suspend fun saveTheme(theme: ThemeOptions) {
        dataStore.edit { prefs ->
            prefs[themeKey] = theme.name
        }
    }

    fun getTheme(): Flow<ThemeOptions> {
        return dataStore.data.map { prefs ->
            ThemeOptions.valueOf(
                prefs[themeKey] ?: ThemeOptions.SYSTEM.name
            )
        }
    }
}