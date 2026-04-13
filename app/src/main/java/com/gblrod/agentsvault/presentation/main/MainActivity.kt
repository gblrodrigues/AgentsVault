package com.gblrod.agentsvault.presentation.main

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
import androidx.navigation.compose.rememberNavController
import com.gblrod.agentsvault.components.BottomBar
import com.gblrod.agentsvault.components.TopBar
import com.gblrod.agentsvault.core.locale.LanguageManager
import com.gblrod.agentsvault.language.viewmodel.LanguageViewModel
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.navigation.NavigationGraph
import com.gblrod.agentsvault.navigation.Routes
import com.gblrod.agentsvault.navigation.drawer.DrawerContent
import com.gblrod.agentsvault.presentation.agents.components.SearchOptions
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.theme.AgentTheme
import com.gblrod.agentsvault.presentation.theme.viewmodel.ThemeViewModel
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel


val Context.dataStore by preferencesDataStore(name = "agent_favorite")

class MainActivity : ComponentActivity() {

    private val prefsDataStore: PrefsDataStore by inject()

    override fun attachBaseContext(newBase: Context) {
        val context = runBlocking {
            val savedLanguage = try {
                prefsDataStore.getLanguageOnce()
            } catch (e: Exception) {
                null
            }

            val locale = LanguageManager.resolveLocale(savedLanguage)
            LanguageManager.applyLocale(newBase, locale)
        }
        super.attachBaseContext( context)
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

            val themeViewModel: ThemeViewModel = koinViewModel()
            val languageViewModel: LanguageViewModel = koinViewModel()
            val agentsViewModel: AgentsViewModel = koinViewModel()

            val theme by themeViewModel.theme.collectAsState()

            val drawerState = rememberDrawerState(
                initialValue = DrawerValue.Closed
            )

            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            var searchType by remember { mutableStateOf(SearchOptions.NONE) }

            if (theme != null) {
                AgentTheme(themeOption = theme!!) {

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
                                    themeViewModel = themeViewModel,
                                    languageViewModel = languageViewModel
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            topBar = {
                                if (searchType == SearchOptions.NONE) {
                                    TopBar(
                                        onOpenDrawer = {
                                            scope.launch {
                                                if (drawerState.isClosed) {
                                                    drawerState.open()
                                                } else {
                                                    drawerState.close()
                                                }
                                            }
                                        },
                                        onSearchClick = {
                                            searchType =
                                                when (navController.currentDestination?.route) {
                                                    Routes.Maps.route -> SearchOptions.MAP
                                                    Routes.Agents.route -> SearchOptions.AGENT
                                                    Routes.Favorites.route -> SearchOptions.AGENT
                                                    else -> SearchOptions.NONE
                                                }
                                        },
                                        viewModel = agentsViewModel
                                    )
                                }
                            },
                            bottomBar = {
                                if (searchType == SearchOptions.NONE) {
                                    BottomBar(navHost = navController)
                                }
                            }
                        ) { paddingValues ->

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(brush = backgroundGradient)
                            ) {
                                NavigationGraph(
                                    navHost = navController,
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
}