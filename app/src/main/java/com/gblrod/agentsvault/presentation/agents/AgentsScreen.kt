package com.gblrod.agentsvault.presentation.agents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gblrod.agentsvault.components.ErrorMessage
import com.gblrod.agentsvault.components.LoadingScreen
import com.gblrod.agentsvault.dto.AgentsUiState
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.presentation.agents.components.AgentContent
import com.gblrod.agentsvault.presentation.agents.components.AgentSearchBar
import com.gblrod.agentsvault.presentation.agents.components.SearchOptions
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.retry.RetryViewModel

@Composable
fun AgentsScreen(
    viewModel: AgentsViewModel,
    prefsDataStore: PrefsDataStore,
    paddingValues: PaddingValues,
    retryViewModel: RetryViewModel,
    searchType: SearchOptions,
    onSearchClose: () -> Unit
) {
    val selectAgent by viewModel.selectAgent.collectAsState()
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val favorites by prefsDataStore.agentFavoriteFlow.collectAsState(initial = emptySet())
    val uiState by viewModel.agentsUiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.observeAgentsRetry(retryViewModel)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when (val state = uiState) {

            is AgentsUiState.Loading -> {
                LoadingScreen()
            }

            is AgentsUiState.Success -> {
                val agents = state.agents
                val currentAgent = selectAgent ?: agents.firstOrNull()

                LaunchedEffect(agents) {
                    if (selectAgent == null && agents.isNotEmpty()) {
                        viewModel.selectAgent(agents.first())
                    }
                }
                when {
                    searchType == SearchOptions.AGENT -> {
                        AgentSearchBar(
                            agents = agents,
                            onAgentSelected = { agent ->
                                viewModel.selectAgent(agent)
                                showAbilitiesSheet = false
                                onSearchClose()
                            },
                            onSearchClose = {
                                onSearchClose()
                            },
                            viewModel = viewModel
                        )
                    }

                    else -> {
                        AgentContent(
                            agents = agents,
                            currentAgent = currentAgent,
                            favorites = favorites,
                            selectAgent = { agent ->
                                viewModel.selectAgent(agent)

                            },
                            onToggleFavorite = { uuid ->
                                viewModel.toggleFavorite(uuid)
                            },
                            paddingValues = paddingValues,
                            showAbilitiesSheet = showAbilitiesSheet,
                            onShowAbilities = { showAbilitiesSheet = true },
                            onDismissAbilities = { showAbilitiesSheet = false },
                            listState = listState,
                            viewModel = viewModel
                        )
                    }
                }
            }

            is AgentsUiState.Error -> {
                when {
                    searchType == SearchOptions.AGENT -> {
                        AgentSearchBar(
                            agents = emptyList(),
                            onAgentSelected = { agent ->
                                viewModel.selectAgent(agent)
                                showAbilitiesSheet = false
                                onSearchClose()
                            },
                            onSearchClose = onSearchClose,
                            viewModel = viewModel
                        )
                    }

                    else -> {
                        ErrorMessage(
                            message = state.message,
                            retryViewModel = retryViewModel
                        )
                    }
                }
            }
        }
    }
}