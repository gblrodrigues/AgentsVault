package com.gblrod.agentsvault.presentation.agents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gblrod.agentsvault.components.ErrorMessage
import com.gblrod.agentsvault.components.LoadingScreen
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.dto.AgentsUiState
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.presentation.agents.components.AgentContent
import com.gblrod.agentsvault.presentation.agents.components.AgentSearchBar
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.retry.RetryViewModel
import com.gblrod.agentsvault.presentation.theme.ThemeViewModel
import kotlinx.coroutines.launch

@Composable
fun AgentsScreen(
    viewModel: AgentsViewModel,
    prefsDataStore: PrefsDataStore,
    paddingValues: PaddingValues,
    onSearchExpanded: (Boolean) -> Unit,
    themeViewModel: ThemeViewModel,
    retryViewModel: RetryViewModel
) {
    var selectAgent by remember { mutableStateOf<AgentDto?>(null) }
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val favorites by prefsDataStore.agentFavoriteFlow.collectAsState(initial = emptySet())
    var searchExpanded by remember { mutableStateOf(false) }
    val uiState by viewModel.agentsUiState.collectAsState()

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
                AgentSearchBar(
                    agents = agents,
                    onAgentSelected = {
                        selectAgent = it
                        showAbilitiesSheet = false
                        searchExpanded = false
                    },
                    searchExpanded = { expanded ->
                        searchExpanded = expanded
                        onSearchExpanded(expanded)
                    },
                    themeViewModel = themeViewModel
                )

                if (!searchExpanded) {
                    AgentContent(
                        agents = agents,
                        currentAgent = currentAgent,
                        favorites = favorites,
                        selectAgent = { selectAgent = it },
                        onToggleFavorite = { uuid ->
                            scope.launch {
                                prefsDataStore.toggleFavorite(
                                    idAgent = uuid
                                )
                            }
                        },
                        paddingValues = paddingValues,
                        showAbilitiesSheet = showAbilitiesSheet,
                        onShowAbilities = { showAbilitiesSheet = true },
                        onDismissAbilities = { showAbilitiesSheet = false }
                    )
                }
            }

            is AgentsUiState.Error -> {
                ErrorMessage(
                    message = state.message,
                    retryViewModel = retryViewModel
                )
            }
        }
    }
}