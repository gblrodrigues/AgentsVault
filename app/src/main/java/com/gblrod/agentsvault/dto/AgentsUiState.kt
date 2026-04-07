package com.gblrod.agentsvault.dto

sealed class AgentsUiState {
    object Loading : AgentsUiState()
    data class Success(
        val agents: List<AgentDto>
    ) : AgentsUiState()

    data class Error(
        val message: String
    ) : AgentsUiState()
}