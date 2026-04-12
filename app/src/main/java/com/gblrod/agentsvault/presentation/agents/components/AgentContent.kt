package com.gblrod.agentsvault.presentation.agents.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.R
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.ui.theme.ButtonAbilityColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentContent(
    agents: List<AgentDto>,
    currentAgent: AgentDto?,
    selectAgent: (AgentDto) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    paddingValues: PaddingValues,
    showAbilitiesSheet: Boolean,
    onShowAbilities: () -> Unit,
    onDismissAbilities: () -> Unit,
    listState: LazyListState,
    viewModel: AgentsViewModel
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var openDialog by remember { mutableStateOf(false) }
    var removedAgent by remember { mutableStateOf<AgentDto?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(currentAgent) {
        currentAgent?.let { selected ->
            val index = agents.indexOfFirst { it.uuid == selected.uuid }
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    currentAgent?.let { agent ->
        val agentIsFavorite = favorites.contains(agent.uuid)

        Box(modifier = Modifier.fillMaxSize()) {

            if (showAbilitiesSheet) {
                AgentBottomSheet(
                    agent = agent,
                    sheetState = sheetState,
                    onDismiss = onDismissAbilities
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = agent.fullPortrait,
                    contentDescription = stringResource(id = R.string.cd_agent_image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxHeight(0.65f)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 105.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(520.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = if (agentIsFavorite) Color.Yellow else MaterialTheme.colorScheme.inverseSurface
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = agent.displayName.uppercase(),
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.align(Alignment.Center)
                                    )

                                    IconToggleButton(
                                        checked = agentIsFavorite,
                                        onCheckedChange = {
                                            if (agentIsFavorite) {
                                                openDialog = true
                                            } else {
                                                viewModel.toggleFavorite(agent.uuid)
                                            }
                                        },
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        Icon(
                                            imageVector = if (agentIsFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                            contentDescription = stringResource(id = R.string.cd_favorite_toggle),
                                            tint = if (agentIsFavorite) Color.Yellow else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(50.dp)
                                        )
                                    }
                                    if (openDialog) {
                                        val message = stringResource(
                                            id = R.string.favorite_remove_success,
                                            currentAgent.displayName
                                        )
                                        val undoTextAction =
                                            stringResource(id = R.string.snackbar_undo)

                                        AgentDialogFavorite(
                                            title = stringResource(
                                                id = R.string.favorite_remove_title,
                                                currentAgent.displayName
                                            ),
                                            description = stringResource(
                                                id = R.string.favorite_remove_description,
                                                currentAgent.displayName
                                            ),
                                            onConfirm = {
                                                removedAgent = agent
                                                viewModel.toggleFavorite(agent.uuid)
                                                scope.launch {
                                                    val result = snackbarHostState.showSnackbar(
                                                        message = message,
                                                        actionLabel = undoTextAction,
                                                        duration = SnackbarDuration.Short
                                                    )

                                                    if (result == SnackbarResult.ActionPerformed) {
                                                        viewModel.restoreFavorite(agent)
                                                        scope.launch {
                                                            delay(50)
                                                            selectAgent(agent)
                                                        }
                                                    }
                                                }
                                                openDialog = false
                                            },
                                            onDismiss = {
                                                openDialog = false
                                            }
                                        )
                                    }
                                }

                                Spacer(Modifier.height(4.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = agent.role.displayIcon,
                                        contentDescription = stringResource(id = R.string.cd_agent_role_icon),
                                        modifier = Modifier.size(14.dp),
                                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
                                    )

                                    Spacer(Modifier.width(6.dp))

                                    Text(
                                        text = agent.role.displayName.uppercase(),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Button(
                                onClick = onShowAbilities,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ButtonAbilityColor
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.button_show_abilities),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = paddingValues.calculateBottomPadding() + 12.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(17.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = agents,
                        key = { it.uuid }
                    ) { item ->
                        Card(
                            border = if (currentAgent.uuid == item.uuid) BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.inverseSurface
                            ) else null,
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            AsyncImage(
                                model = item.displayIcon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable { selectAgent(item) }
                            )
                        }
                    }
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = paddingValues.calculateBottomPadding() + 70.dp)
            )
        }
    }
}