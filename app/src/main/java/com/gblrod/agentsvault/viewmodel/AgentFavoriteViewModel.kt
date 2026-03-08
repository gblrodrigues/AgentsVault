package com.gblrod.agentsvault.viewmodel

import androidx.lifecycle.ViewModel
import com.gblrod.agentsvault.local.AgentFavoriteDataStore

class AgentFavoriteViewModel(
    private val repository: AgentFavoriteDataStore
) : ViewModel(){

    val isFavoriteAgent = repository.isFavorite

    suspend fun isFavorite(value: Boolean) {
        repository.agentFavoritePref(value)
    }
}