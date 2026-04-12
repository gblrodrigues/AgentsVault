package com.gblrod.agentsvault.dto

sealed class MapsUiState {
    object Loading : MapsUiState()
    data class Success(
        val maps: List<MapDto>
    ) : MapsUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : MapsUiState()
}