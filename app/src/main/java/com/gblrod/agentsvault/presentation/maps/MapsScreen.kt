package com.gblrod.agentsvault.presentation.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gblrod.agentsvault.components.ErrorMessage
import com.gblrod.agentsvault.components.LoadingScreen
import com.gblrod.agentsvault.dto.MapDto
import com.gblrod.agentsvault.dto.MapsUiState
import com.gblrod.agentsvault.presentation.maps.components.MapContent
import com.gblrod.agentsvault.presentation.maps.components.MapSearchBar
import com.gblrod.agentsvault.presentation.maps.viewmodel.MapsViewModel
import com.gblrod.agentsvault.presentation.retry.RetryViewModel
import com.gblrod.agentsvault.presentation.theme.ThemeViewModel

@Composable
fun MapsScreen(
    viewModel: MapsViewModel,
    themeViewModel: ThemeViewModel,
    onSearchExpanded: (Boolean) -> Unit,
    paddingValues: PaddingValues,
    retryViewModel: RetryViewModel
) {
    var selectMap by remember { mutableStateOf<MapDto?>(null) }
    var searchExpanded by remember { mutableStateOf(false) }
    val uiState by viewModel.mapsUiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.observeMapsRetry(retryViewModel)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when (val state = uiState) {
            is MapsUiState.Loading -> {
                LoadingScreen()
            }

            is MapsUiState.Success -> {
                val maps = state.maps
                val currentMap = selectMap ?: maps.firstOrNull()
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
                        paddingValues = paddingValues,
                        listState = listState
                    )
                }
            }

            is MapsUiState.Error -> {
                MapSearchBar(
                    maps = emptyList(),
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
                    ErrorMessage(
                        message = state.message,
                        retryViewModel = retryViewModel
                    )
                }
            }
        }
    }
}