package com.gblrod.agentsvault.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.dto.AgentDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentSearchBar(
    agents: List<AgentDto>,
    onAgentSelected: (AgentDto) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val filteredAgents = agents.filter {
        it.displayName.contains(query, ignoreCase = true)
    }

    BackHandler(enabled = expanded) {
        expanded = false
        query = ""
    }

    SearchBar(
        expanded = expanded,
        onExpandedChange = { expanded = it },

        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!expanded) Modifier.padding(16.dp)
                else Modifier
            ),

        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                    expanded = true
                },
                onSearch = {},
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = {
                    Text(
                        text = "Ex: Clove",
                        color = Color.White
                    )
                },
                leadingIcon = {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Ícone de Voltar",
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    query = ""
                                }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ícone de Pesquisar"
                        )
                    }
                },
                trailingIcon = {
                    if (query.isNotBlank()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Ícone de Limpar campo",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                expanded = false
                                query = ""
                            }
                        )
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(
                    cursorColor = Color.White
                )
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.Gray,
            dividerColor = Color.DarkGray,
        )
    )
    {
            LazyColumn {
                items(filteredAgents) { agent ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = agent.displayName
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
                            },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Gray
                        )
                    )
                }
            }
    }
}