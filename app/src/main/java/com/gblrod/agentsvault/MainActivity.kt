package com.gblrod.agentsvault

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.datastore.preferences.preferencesDataStore
import com.gblrod.agentsvault.local.AgentFavoriteDataStore
import com.gblrod.agentsvault.navigation.NavHostController
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo


private val Context.dataStore by preferencesDataStore(name = "agent_favorite")
class MainActivity : ComponentActivity() {
    private val repository by lazy {
        AgentFavoriteDataStore(applicationContext.dataStore)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                BackgroundColorOne,
                                BackgroundColorTwo
                            )
                        )
                    )
            ) {
                NavHostController(repository)
            }
        }
    }
}