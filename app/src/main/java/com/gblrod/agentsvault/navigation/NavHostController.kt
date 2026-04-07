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
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.maps.viewmodel.MapsViewModel
import com.gblrod.agentsvault.presentation.retry.RetryViewModel
import com.gblrod.agentsvault.presentation.theme.ThemeViewModel

@Composable
fun NavHostController(
    navHost: NavHostController,
    repository: PrefsDataStore,
    paddingValues: PaddingValues,
    searchExpanded: (Boolean) -> Unit,
    themeViewModel: ThemeViewModel
) {
    val agentsViewModel: AgentsViewModel = viewModel()
    val mapsViewModel: MapsViewModel = viewModel()
    val retryViewModel: RetryViewModel = viewModel()

    NavHost(
        navController = navHost,
        startDestination = "main",
    )
    {
        composable("main") {

            AgentsScreen(
                viewModel = agentsViewModel,
                prefsDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded,
                themeViewModel = themeViewModel,
                retryViewModel = retryViewModel
            )
        }

        composable("favorites") {
            AgentFavoriteScreen(
                viewModel = agentsViewModel,
                onFavoriteScreen = { navHost.popBackStack() },
                prefsDataStore = repository,
                paddingValues = paddingValues,
                onSearchExpanded = searchExpanded,
                themeViewModel = themeViewModel,
                retryViewModel = retryViewModel
            )
        }

        composable("maps") {
            MapsScreen(
                viewModel = mapsViewModel,
                themeViewModel = themeViewModel,
                onSearchExpanded = searchExpanded,
                paddingValues = paddingValues,
                retryViewModel = retryViewModel
            )
        }
    }
}