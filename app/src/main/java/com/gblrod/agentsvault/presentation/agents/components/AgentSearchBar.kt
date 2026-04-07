package com.gblrod.agentsvault.presentation.agents.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.components.ThemeMenuButton
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.presentation.theme.ThemeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentSearchBar(
    agents: List<AgentDto>,
    onAgentSelected: (AgentDto) -> Unit,
    searchExpanded: (Boolean) -> Unit,
    themeViewModel: ThemeViewModel
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val filteredAgents = agents.filter {
        it.displayName.contains(query, ignoreCase = true)
    }
    val filterRandomNameAgent = agents.firstOrNull()?.displayName
    var placeHolder by remember { mutableStateOf(filterRandomNameAgent) }
    val currentAgents by rememberUpdatedState(agents)
    val scope = rememberCoroutineScope()
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val newAgentName = currentAgents
                .filter { it.displayName != placeHolder }
                .randomOrNull()
                ?.displayName

            if (newAgentName != null) {
                placeHolder = newAgentName
            }
        }
    }

    BackHandler(enabled = expanded) {
        expanded = false
        query = ""
        searchExpanded(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = if (expanded) {
                Modifier.fillMaxWidth()
            } else {
                Modifier.padding(horizontal = 8.dp)
    },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                expanded = expanded,
                onExpandedChange = {
                    expanded = it
                    searchExpanded(it)
                },

                modifier = if (expanded) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.weight(1f)
                },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = {
                            query = it
                            expanded = true
                            searchExpanded(true)
                        },
                        onSearch = {
                            scope.launch {
                                focus.clearFocus(force = true)
                                keyboardController?.hide()
                            }
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = it
                            searchExpanded(it)
                        },
                        placeholder = {
                            Text(
                                text = "$placeHolder",
                                color = if (expanded) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    MaterialTheme.colorScheme.inverseOnSurface
                                },
                            )
                        },
                        leadingIcon = {
                            if (expanded) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Ícone de Voltar",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .clickable {
                                            expanded = false
                                            query = ""
                                            searchExpanded(false)
                                        }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Ícone de Pesquisar",
                                    tint = MaterialTheme.colorScheme.inverseOnSurface
                                )
                            }
                        },
                        trailingIcon = {
                            if (query.isNotBlank()) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Ícone de Limpar campo",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.clickable {
                                        expanded = false
                                        query = ""
                                        searchExpanded(false)
                                    }
                                )
                            }
                        },
                        colors = SearchBarDefaults.inputFieldColors(
                            cursorColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = if (expanded) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        MaterialTheme.colorScheme.inverseSurface
                    },
                    dividerColor = MaterialTheme.colorScheme.outline
                )
            )
            {
                LazyColumn {
                    items(filteredAgents) { agent ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = agent.displayName,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            leadingContent = {
                                AsyncImage(
                                    model = agent.displayIcon,
                                    contentDescription = agent.displayName,
                                    modifier = Modifier.size(40.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAgentSelected(agent)
                                    query = ""
                                    expanded = false
                                    searchExpanded(false)
                                },
                            colors = ListItemDefaults.colors(
                                containerColor = if (expanded) {
                                    MaterialTheme.colorScheme.surface
                                } else {
                                    MaterialTheme.colorScheme.inverseSurface
                                }
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.offset(y = 17.dp),
                contentAlignment = Alignment.Center
            ) {
                ThemeMenuButton(themeViewModel)
            }
        }
    }
}