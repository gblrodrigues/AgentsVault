package com.gblrod.agentsvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gblrod.agentsvault.screen.AgentsScreen
import com.gblrod.agentsvault.ui.theme.AgentsVaultTheme
import com.gblrod.agentsvault.viewmodel.AgentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgentsVaultTheme {
                AgentsScreen(viewModel = AgentViewModel())
            }
        }
    }
}