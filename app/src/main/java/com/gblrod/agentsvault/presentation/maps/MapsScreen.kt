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
import androidx.compose.ui.res.stringResource
import com.gblrod.agentsvault.components.ErrorMessage
import com.gblrod.agentsvault.components.LoadingScreen
import com.gblrod.agentsvault.presentation.agents.components.SearchOptions
import com.gblrod.agentsvault.dto.MapDto
import com.gblrod.agentsvault.dto.MapsUiState
import com.gblrod.agentsvault.presentation.maps.components.MapContent
import com.gblrod.agentsvault.presentation.maps.components.MapSearchBar
import com.gblrod.agentsvault.presentation.maps.viewmodel.MapsViewModel
import com.gblrod.agentsvault.presentation.retry.viewmodel.RetryViewModel

@Composable
fun MapsScreen(
    viewModel: MapsViewModel,
    paddingValues: PaddingValues,
    retryViewModel: RetryViewModel,
    searchType: SearchOptions,
    onSearchClose: () -> Unit
) {
    var selectMap by remember { mutableStateOf<MapDto?>(null) }
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
                when {
                    searchType == SearchOptions.MAP -> {
                        MapSearchBar(
                            maps = maps,
                            onMapSelected = {
                                selectMap = it
                                onSearchClose()
                            },
                            onSearchClose = { onSearchClose() }
                        )
                    }

                    else -> {
                        MapContent(
                            maps = maps,
                            currentMap = currentMap,
                            selectMap = { selectMap = it },
                            paddingValues = paddingValues,
                            listState = listState
                        )
                    }
                }
            }

            is MapsUiState.Error -> {
                val message = if (state.code == null) {
                    stringResource(id = state.messageResId)
                } else {
                    stringResource(id = state.messageResId, state.code)
                }
                when {
                    searchType == SearchOptions.MAP -> {
                        MapSearchBar(
                            maps = emptyList(),
                            onMapSelected = {
                                selectMap = it
                            },
                            onSearchClose = { onSearchClose() }
                        )
                    }

                    else -> {
                        ErrorMessage(
                            message = message,
                            retryViewModel = retryViewModel
                        )
                    }
                }
            }
        }
    }
}