package com.gblrod.agentsvault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gblrod.agentsvault.local.AgentFavoriteDataStore

class AgentFavoriteViewModelFactory(
    private val repository: AgentFavoriteDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AgentFavoriteViewModel::class.java)) {
            return AgentFavoriteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}