package com.gblrod.agentsvault.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.screen.AgentFavoriteScreen
import com.gblrod.agentsvault.screen.AgentsScreen
import com.gblrod.agentsvault.viewmodel.AgentFavoriteViewModel
import com.gblrod.agentsvault.viewmodel.AgentFavoriteViewModelFactory
import com.gblrod.agentsvault.viewmodel.AgentViewModel


@Composable
fun NavHostController(
    repository: AgentFavoriteDataStore
) {
    val navHost = rememberNavController()
    val agentViewModel: AgentViewModel = viewModel()
    val agentFavoriteViewModel: AgentFavoriteViewModel = viewModel(
        factory = AgentFavoriteViewModelFactory(repository)
    )

    NavHost(
        navController = navHost,
        startDestination = "main",
    )
    {
        composable("main") {

            AgentsScreen(
                viewModel = agentViewModel,
                agentFavoriteViewModel = agentFavoriteViewModel,
                agentFavoriteDataStore = repository,
                onFavoriteScreen = {
                    navHost.navigate("favorites")
                }
            )
        }

        composable("favorites") {
            AgentFavoriteScreen(
                onFavoriteScreen = {
                    navHost.popBackStack()
                },
                viewModel = agentViewModel,
                agentFavoriteDataStore = repository,
            )
        }
    }
}