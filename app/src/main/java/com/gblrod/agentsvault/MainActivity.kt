package com.gblrod.agentsvault

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gblrod.agentsvault.ui.theme.AgentTheme
import com.gblrod.agentsvault.components.BottomBar
import com.gblrod.agentsvault.navigation.drawer.DrawerContent
import com.gblrod.agentsvault.presentation.agents.components.SearchOptions
import com.gblrod.agentsvault.components.TopBar
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.navigation.NavHostController
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo
import com.gblrod.agentsvault.ui.theme.ThemeViewModel
import com.gblrod.agentsvault.ui.theme.ThemeViewModelFactory
import kotlinx.coroutines.launch


private val Context.dataStore by preferencesDataStore(name = "agent_favorite")

class MainActivity : ComponentActivity() {
    private val repository by lazy {
        PrefsDataStore(applicationContext.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val backgroundGradient = remember {
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundColorOne,
                        BackgroundColorTwo
                    )
                )
            }
            val factory = ThemeViewModelFactory(repository)
            val themeViewModel: ThemeViewModel = viewModel(factory = factory)
            val theme by themeViewModel.theme.collectAsState()
            val drawerState = rememberDrawerState(
                initialValue = DrawerValue.Closed
            )
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            var searchType by remember { mutableStateOf(SearchOptions.NONE) }
            val agentsViewModel: AgentsViewModel = viewModel()

            AgentTheme(
                themeOption = theme
            ) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerContent(
                                navHost = navController,
                                onItemClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                themeViewModel = themeViewModel
                            )
                        }
                    }
                )
                {
                    Scaffold(
                        topBar = {
                            if (searchType == SearchOptions.NONE) {
                                TopBar(
                                    onOpenDrawer = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },
                                    onSearchClick = {
                                        searchType = when (navController.currentDestination?.route) {
                                            "maps" -> SearchOptions.MAP
                                            "main" -> SearchOptions.AGENT
                                            "favorites" -> SearchOptions.AGENT
                                            else -> SearchOptions.NONE
                                        }
                                    },
                                    viewModel = agentsViewModel
                                )
                            }
                        },
                        bottomBar = {
                            if (searchType == SearchOptions.NONE) {
                                BottomBar(
                                    navHost = navController
                                )
                            }
                        },
                    ) { paddingValues ->

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(brush = backgroundGradient)) {

                            NavHostController(
                                navHost = navController,
                                repository = repository,
                                paddingValues = paddingValues,
                                searchType = searchType,
                                onSearchClose = {
                                    searchType = SearchOptions.NONE
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}