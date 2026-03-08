package com.gblrod.agentsvault

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.screen.AgentsScreen
import com.gblrod.agentsvault.ui.theme.AgentsVaultTheme
import com.gblrod.agentsvault.viewmodel.AgentFavoriteViewModel
import com.gblrod.agentsvault.viewmodel.AgentViewModel


private val Context.dataStore by preferencesDataStore(name = "agent_favorite")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AgentFavoriteDataStore(dataStore)
        setContent {
            AgentsVaultTheme {
                AgentsScreen(
                    viewModel = AgentViewModel(),
                    agentFavoriteViewModel = AgentFavoriteViewModel(repository = repository),
                    agentFavoriteDataStore = repository
                )
            }
        }
    }
}