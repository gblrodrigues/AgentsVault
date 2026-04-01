package com.gblrod.agentsvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.presentation.favorite.AgentFavoriteScreen
import com.gblrod.agentsvault.presentation.agentsvault.AgentsScreen
import com.gblrod.agentsvault.viewmodel.AgentViewModel

@Composable
fun NavHostController(
    navHost: NavHostController,
    repository: AgentFavoriteDataStore,
    paddingValues: PaddingValues,
    searchExpanded: (Boolean) -> Unit
) {
    val agentViewModel: AgentViewModel = viewModel()

    NavHost(
        navController = navHost,
        startDestination = "main",
    )
    {
        composable("main") {

            AgentsScreen(
                viewModel = agentViewModel,
                agentFavoriteDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded
            )
        }

        composable("favorites") {
            AgentFavoriteScreen(
                viewModel = agentViewModel,
                onFavoriteScreen = { navHost.popBackStack() },
                agentFavoriteDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded
            )
        }
    }
}