package com.gblrod.agentsvault.presentation.retry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RetryViewModel : ViewModel() {
    private val _retryEvent = MutableSharedFlow<Unit>(replay = 1)
    val retryEvent = _retryEvent.asSharedFlow()

    fun retryAll() {
        viewModelScope.launch {
            _retryEvent.emit(Unit)
        }
    }
}