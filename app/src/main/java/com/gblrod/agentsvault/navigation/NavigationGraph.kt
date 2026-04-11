package com.gblrod.agentsvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.presentation.agents.AgentsScreen
import com.gblrod.agentsvault.presentation.agents.components.SearchOptions
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.favorites.AgentFavoriteScreen
import com.gblrod.agentsvault.presentation.maps.MapsScreen
import com.gblrod.agentsvault.presentation.maps.viewmodel.MapsViewModel
import com.gblrod.agentsvault.presentation.retry.viewmodel.RetryViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NavigationGraph(
    navHost: NavHostController,
    paddingValues: PaddingValues,
    searchType: SearchOptions,
    onSearchClose: () -> Unit
) {
    val agentsViewModel: AgentsViewModel = koinViewModel()
    val mapsViewModel: MapsViewModel = koinViewModel()
    val retryViewModel: RetryViewModel = koinViewModel()
    val repository: PrefsDataStore = koinInject()

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
                retryViewModel = retryViewModel,
                searchType = searchType,
                onSearchClose = onSearchClose
            )
        }

        composable("favorites") {
            AgentFavoriteScreen(
                viewModel = agentsViewModel,
                onFavoriteScreen = { navHost.popBackStack() },
                prefsDataStore = repository,
                paddingValues = paddingValues,
                retryViewModel = retryViewModel,
                searchType = searchType,
                onSearchClose = onSearchClose
            )
        }

        composable("maps") {
            MapsScreen(
                viewModel = mapsViewModel,
                paddingValues = paddingValues,
                retryViewModel = retryViewModel,
                searchType = searchType,
                onSearchClose = onSearchClose
            )
        }
    }
}