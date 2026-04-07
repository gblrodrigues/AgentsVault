package com.gblrod.agentsvault.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gblrod.agentsvault.local.PrefsDataStore

class ThemeViewModelFactory(
    private val repository: PrefsDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ThemeViewModel(repository) as T
    }
}