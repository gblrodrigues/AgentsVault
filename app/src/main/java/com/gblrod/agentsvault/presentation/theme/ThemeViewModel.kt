package com.gblrod.agentsvault.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.agentsvault.components.ThemeOptions
import com.gblrod.agentsvault.local.PrefsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val repository: PrefsDataStore
) : ViewModel(){
    val theme = repository.getTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ThemeOptions.SYSTEM
        )

    fun setTheme(theme: ThemeOptions) {
        viewModelScope.launch {
            repository.saveTheme(theme)
        }
    }
}