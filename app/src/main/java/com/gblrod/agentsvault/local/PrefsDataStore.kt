package com.gblrod.agentsvault.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gblrod.agentsvault.language.LanguageOptions
import com.gblrod.agentsvault.presentation.theme.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val toggleFavorite = stringSetPreferencesKey(name = "toggle_favorite")
private val themeKey = stringPreferencesKey(name = "theme")
private val languageKey = stringPreferencesKey(name = "language")

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
            ThemeOptions.entries.find {
                it.name == prefs[themeKey]
            } ?: ThemeOptions.SYSTEM
        }
    }

    suspend fun getLanguageOnce(): LanguageOptions? {
        return dataStore.data.first()[languageKey]?.let {
            LanguageOptions.valueOf(it)
        }
    }

    suspend fun saveLanguage(language: LanguageOptions) {
        dataStore.edit { prefs ->
            prefs[languageKey] = language.name
        }
    }

    fun getLanguage(): Flow<LanguageOptions?> {
        return dataStore.data.map { prefs ->
            prefs[languageKey]?.let {
                LanguageOptions.valueOf(it)
            }
        }
    }
}