package com.gblrod.agentsvault.presentation.agents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.dto.AgentsUiState
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.network.API
import com.gblrod.agentsvault.presentation.retry.viewmodel.RetryViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class AgentsViewModel(
    val api: API,
    private val prefsDataStore: PrefsDataStore
) : ViewModel() {
    private val _agentsUiState = MutableStateFlow<AgentsUiState>(AgentsUiState.Loading)
    val agentsUiState: StateFlow<AgentsUiState> = _agentsUiState
    private var job: Job? = null
    private var cachedAgents: List<AgentDto> = emptyList()
    private val _selectedAgent = MutableStateFlow<AgentDto?>(null)
    val selectAgent: StateFlow<AgentDto?> = _selectedAgent

    init {
        viewModelScope.launch {
            fetchAgents()
        }
    }

    fun observeAgentsRetry(retryViewModel: RetryViewModel) {
        viewModelScope.launch {
            retryViewModel.retryEvent.collect {
                fetchAgents()
            }
        }
    }

    fun fetchAgents() {
        job?.cancel()

        job = viewModelScope.launch {
            val firstLoad = _agentsUiState.value is AgentsUiState.Loading

            if (firstLoad) {
                _agentsUiState.value = AgentsUiState.Loading
            }

            try {
                val agentsResponse = api.findAgents()
                cachedAgents = agentsResponse.data
                _agentsUiState.value = AgentsUiState.Success(cachedAgents)

            } catch (e: IOException) {
                if (cachedAgents.isNotEmpty()) {
                    _agentsUiState.value = AgentsUiState.Success(cachedAgents)
                } else {
                    _agentsUiState.value =
                        AgentsUiState.Error("Erro ao carregar Agentes! \nSem acesso a internet. Verifique sua conexão.")
                }

            } catch (e: HttpException) {
                _agentsUiState.value = AgentsUiState.Error("Erro do servidor: ${e.code()}")

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _agentsUiState.value = AgentsUiState.Error("Ocorreu um erro inesperado!")
            }
        }
    }

    fun selectAgent(agent: AgentDto) {
        _selectedAgent.value = agent
    }

    fun toggleFavorite(uuid: String) {
        viewModelScope.launch {
            prefsDataStore.toggleFavorite(uuid)
        }
    }

    fun restoreFavorite(agent: AgentDto) {
        toggleFavorite(agent.uuid)

        _selectedAgent.value = agent
    }
}