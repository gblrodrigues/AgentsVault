package com.gblrod.agentsvault.presentation.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gblrod.agentsvault.presentation.maps.components.MapContent
import com.gblrod.agentsvault.presentation.maps.components.MapSearchBar
import com.gblrod.agentsvault.dto.MapDto
import com.gblrod.agentsvault.components.LoadingScreen
import com.gblrod.agentsvault.viewmodel.AgentViewModel
import com.gblrod.agentsvault.viewmodel.ThemeViewModel

@Composable
fun MapsScreen(
    viewModel: AgentViewModel,
    themeViewModel: ThemeViewModel,
    onSearchExpanded: (Boolean) -> Unit,
    paddingValues: PaddingValues
) {
    val maps by viewModel.maps.collectAsState()
    var selectMap by remember { mutableStateOf<MapDto?>(null) }
    val currentMap = selectMap ?: maps.firstOrNull()
    var searchExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchMaps()
    }

    if (maps.isEmpty()) {
        LoadingScreen()
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MapSearchBar(
            maps = maps,
            onMapSelected = {
                selectMap = it
                searchExpanded = false
            },
            searchExpanded = { expanded ->
                searchExpanded = expanded
                onSearchExpanded(expanded)
            },
            themeViewModel = themeViewModel
        )

        if (!searchExpanded) {
            MapContent(
                maps = maps,
                currentMap = currentMap,
                selectMap = { selectMap = it },
                paddingValues = paddingValues
            )
        }
    }
}