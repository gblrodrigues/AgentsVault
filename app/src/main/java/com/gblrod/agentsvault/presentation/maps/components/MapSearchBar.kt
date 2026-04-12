package com.gblrod.agentsvault.presentation.maps.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.R
import com.gblrod.agentsvault.dto.MapDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    maps: List<MapDto>,
    onMapSelected: (MapDto) -> Unit,
    onSearchClose: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val filteredMaps = maps.filter {
        it.displayName.contains(query, ignoreCase = true)
    }
    val validMaps = filteredMaps.filter {
        it.displayName.isNotBlank() && it.displayIcon != null && it.splash != null
    }
    val filterRandomNameMap = maps.firstOrNull()?.displayName
    var placeHolder by remember { mutableStateOf(filterRandomNameMap) }
    val scope = rememberCoroutineScope()
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val maxChar = maps.maxOfOrNull { it.displayName.length } ?: 8


    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val newMapName = validMaps
                .filter { it.displayName != placeHolder }
                .randomOrNull()
                ?.displayName

            if (newMapName != null) {
                placeHolder = newMapName
            }
        }
    }

    BackHandler {
        query = ""
        onSearchClose()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            expanded = true,
            onExpandedChange = {
                onSearchClose()
            },

            modifier = Modifier.fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { queryChange ->
                        if (queryChange.length <= maxChar) {
                            query = queryChange
                        }
                    },
                    onSearch = {
                        scope.launch {
                            when {
                                query.isEmpty() -> {
                                    focus.clearFocus(force = true)
                                    keyboardController?.hide()
                                    return@launch
                                }

                                validMaps.isNotEmpty() -> {
                                    val mapOfQuery = filteredMaps.first()

                                    onMapSelected(mapOfQuery)
                                    onSearchClose()
                                }

                                else -> {
                                    focusRequester.requestFocus()
                                }
                            }
                        }
                    },
                    expanded = true,
                    onExpandedChange = {
                        expanded = false
                    },
                    placeholder = {
                        if (validMaps.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.searchbar_placeholder_empty),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = "$placeHolder",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.searchbar_cd_leading_icon),
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clickable {
                                    query = ""
                                    onSearchClose()
                                }
                        )
                    },
                    trailingIcon = {
                        if (query.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(id = R.string.searchbar_cd_trailing_icon),
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.clickable {
                                    query = ""
                                }
                            )
                        }
                    },
                    modifier = Modifier.focusRequester(focusRequester),
                    colors = SearchBarDefaults.inputFieldColors(
                        cursorColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                dividerColor = MaterialTheme.colorScheme.outline
            )
        )
        {
            LazyColumn {
                items(validMaps) { map ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = map.displayName,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingContent = {
                            AsyncImage(
                                model = map.displayIcon,
                                contentDescription = map.displayName,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onMapSelected(map)
                                query = ""
                                onSearchClose()
                            },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    }
}