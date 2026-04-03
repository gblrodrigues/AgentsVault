package com.gblrod.agentsvault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gblrod.agentsvault.local.PrefsDataStore

class AgentViewModelFactory(
    private val repository: PrefsDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}