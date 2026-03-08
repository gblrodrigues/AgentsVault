package com.gblrod.agentsvault.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val agentFavoritePref = booleanPreferencesKey(name = "agent_favorite")
private val toggleFavorite = stringSetPreferencesKey(name = "toggle_favorite")

class AgentFavoriteDataStore(
    private val dataStore: DataStore<Preferences>
) {
    val isFavorite = dataStore.data
        .map { prefs ->
            prefs[agentFavoritePref] ?: false
        }

    suspend fun agentFavoritePref(value: Boolean) {
        dataStore.edit { prefs ->
            prefs[agentFavoritePref] = value
        }
    }

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
}