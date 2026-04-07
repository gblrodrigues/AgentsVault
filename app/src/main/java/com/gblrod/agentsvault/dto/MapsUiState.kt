package com.gblrod.agentsvault.dto

sealed class MapsUiState {
    object Loading : MapsUiState()
    data class Success(
        val maps: List<MapDto>
    ) : MapsUiState()

    data class Error(
        val message: String
    ) : MapsUiState()
}