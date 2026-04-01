package com.gblrod.agentsvault.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.agentsvault.components.AgentContent
import com.gblrod.agentsvault.components.AgentSearchBar
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.ui.theme.ButtonAbilityColor
import com.gblrod.agentsvault.viewmodel.AgentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentFavoriteScreen(
    viewModel: AgentViewModel,
    agentFavoriteDataStore: AgentFavoriteDataStore,
    paddingValues: PaddingValues,
    onSearchExpanded: (Boolean) -> Unit,
    onFavoriteScreen: () -> Unit,
) {
    val agents by viewModel.agents.collectAsState()
    val favorites by agentFavoriteDataStore.agentFavoriteFlow.collectAsState(initial = emptySet())
    val favoriteAgents = agents.filter { agent -> favorites.contains(agent.uuid) }
    var selectAgent by remember { mutableStateOf<AgentDto?>(null) }
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val currentAgent = selectAgent ?: favoriteAgents.firstOrNull()
    var searchExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = favorites, key2 = agents) {
        val current = selectAgent
        if (current == null || !favorites.contains(current.uuid)) {
            selectAgent = favoriteAgents.firstOrNull()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (favoriteAgents.isEmpty() || currentAgent == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Ícone de Sem favorito",
                    tint = Color.Black,
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sem agente favorito",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Quando você favoritar agentes, \neles aparecerão aqui.",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onFavoriteScreen()
                    },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonAbilityColor
                    )
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Ícone de Retornar para main",
                            tint = Color.Black,
                            modifier = Modifier.padding(horizontal = 7.dp)
                        )

                        Text(
                            text = "Favoritar Agentes",
                            color = Color.Black
                        )
                    }
                }
            }
        } else {
            AgentSearchBar(
                agents = favoriteAgents,
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
                    agents = favoriteAgents,
                    currentAgent = currentAgent,
                    favorites = favorites,
                    selectAgent = { selectAgent = it },
                    onToggleFavorite = { uuid ->
                        scope.launch {
                            agentFavoriteDataStore.toggleFavorite(uuid)
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
}