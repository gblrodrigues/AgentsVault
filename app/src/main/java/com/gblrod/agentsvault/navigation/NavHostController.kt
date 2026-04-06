package com.gblrod.agentsvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.presentation.favorites.AgentFavoriteScreen
import com.gblrod.agentsvault.presentation.agents.AgentsScreen
import com.gblrod.agentsvault.presentation.maps.MapsScreen
import com.gblrod.agentsvault.viewmodel.AgentViewModel
import com.gblrod.agentsvault.viewmodel.ThemeViewModel

@Composable
fun NavHostController(
    navHost: NavHostController,
    repository: PrefsDataStore,
    paddingValues: PaddingValues,
    searchExpanded: (Boolean) -> Unit,
    themeViewModel: ThemeViewModel
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
                prefsDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded,
                themeViewModel = themeViewModel
            )
        }

        composable("favorites") {
            AgentFavoriteScreen(
                viewModel = agentViewModel,
                onFavoriteScreen = { navHost.popBackStack() },
                prefsDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded,
                themeViewModel = themeViewModel
            )
        }

        composable("maps") {
            MapsScreen(
                viewModel = agentViewModel,
                themeViewModel = themeViewModel,
                onSearchExpanded = searchExpanded,
                paddingValues = paddingValues
            )
        }
    }
}