package com.gblrod.agentsvault

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gblrod.agentsvault.components.AgentTheme
import com.gblrod.agentsvault.components.BottomBar
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.navigation.NavHostController
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo
import com.gblrod.agentsvault.viewmodel.AgentViewModelFactory
import com.gblrod.agentsvault.viewmodel.ThemeViewModel


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
            val factory = AgentViewModelFactory(repository)
            val themeViewModel: ThemeViewModel = viewModel(factory = factory)
            val theme by themeViewModel.theme.collectAsState()

            AgentTheme(
                themeOption = theme
            ) {
                val navController = rememberNavController()
                var showBottomBar by remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
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
                            searchExpanded = { isOpen ->
                                showBottomBar = !isOpen
                            },
                            themeViewModel = themeViewModel
                        )
                    }
                }
            }
        }
    }
}