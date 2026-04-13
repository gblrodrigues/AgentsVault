package com.gblrod.agentsvault.language.viewmodel

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.agentsvault.language.LanguageOptions
import com.gblrod.agentsvault.local.PrefsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val repository: PrefsDataStore
) : ViewModel() {
    val language = repository.getLanguage()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun setLanguage(language: LanguageOptions, finished: () -> Unit) {
        viewModelScope.launch {
            repository.saveLanguage(language = language)
            finished()
        }
    }

    fun applyLanguage(language: LanguageOptions) {
        val tag = when (language) {
            LanguageOptions.PT_BR -> "pt-BR"
            LanguageOptions.EN_US -> "en"
            LanguageOptions.ES -> "es"
        }

        Log.d("LANG_DEBUG", "Applying language: $tag")
    }
}