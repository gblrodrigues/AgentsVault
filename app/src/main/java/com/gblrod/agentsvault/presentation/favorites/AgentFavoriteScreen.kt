package com.gblrod.agentsvault.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.gblrod.agentsvault.presentation.favorites.components.EmptyFavorites
import com.gblrod.agentsvault.presentation.retry.RetryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentFavoriteScreen(
    viewModel: AgentsViewModel,
    prefsDataStore: PrefsDataStore,
    paddingValues: PaddingValues,
    onFavoriteScreen: () -> Unit,
    retryViewModel: RetryViewModel,
    searchType: SearchOptions,
    onSearchClose: () -> Unit
) {
    val favorites by prefsDataStore.agentFavoriteFlow.collectAsState(initial = emptySet())
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val uiState by viewModel.agentsUiState.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (val state = uiState) {
            is AgentsUiState.Loading -> {
                LoadingScreen()
            }

            is AgentsUiState.Success -> {
                val favoriteAgents = state.agents.filter { favorites.contains(it.uuid) }
                val selectedAgent by viewModel.selectAgent.collectAsState()
                val currentAgent = selectedAgent ?: favoriteAgents.firstOrNull()

                LaunchedEffect(favoriteAgents) {
                    val current = selectedAgent

                    if (favoriteAgents.isNotEmpty() &&
                        (current == null || favoriteAgents.none { it.uuid == current.uuid })
                    ) {
                        viewModel.selectAgent(favoriteAgents.first())
                    }
                }

                when {
                    searchType == SearchOptions.AGENT -> {
                        AgentSearchBar(
                            agents = favoriteAgents,
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

                    favoriteAgents.isEmpty() || currentAgent == null -> {
                        EmptyFavorites(
                            onFavoriteClick = {
                                onFavoriteScreen()
                            }
                        )
                    }

                    else -> {
                        AgentContent(
                            agents = favoriteAgents,
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
                            currentAgent = currentAgent,
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
                            onSearchClose = {
                                onSearchClose()
                            },
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