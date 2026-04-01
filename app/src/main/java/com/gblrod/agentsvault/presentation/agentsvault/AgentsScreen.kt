package com.gblrod.agentsvault.presentation.agentsvault

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
import com.gblrod.agentsvault.components.AgentContent
import com.gblrod.agentsvault.components.AgentSearchBar
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.viewmodel.AgentViewModel
import kotlinx.coroutines.launch

@Composable
fun AgentsScreen(
    viewModel: AgentViewModel,
    agentFavoriteDataStore: AgentFavoriteDataStore,
    paddingValues: PaddingValues,
    onSearchExpanded: (Boolean) -> Unit
) {
    val agents by viewModel.agents.collectAsState()
    var selectAgent by remember { mutableStateOf<AgentDto?>(null) }
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val favorites by agentFavoriteDataStore.agentFavoriteFlow.collectAsState(initial = emptySet())
    val currentAgent = selectAgent ?: agents.firstOrNull()
    var searchExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAgents()
    }

    if (agents.isEmpty()) {
        AgentsLoadingScreen()
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
            }
        )

        if (!searchExpanded) {
            AgentContent(
                agents = agents,
                currentAgent = currentAgent,
                favorites = favorites,
                selectAgent = { selectAgent = it },
                onToggleFavorite = { uuid ->
                    scope.launch {
                        agentFavoriteDataStore.toggleFavorite(
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
}