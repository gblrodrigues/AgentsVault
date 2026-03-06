package com.gblrod.agentsvault.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.components.AgentBottomSheet
import com.gblrod.agentsvault.components.AgentSearchBar
import com.gblrod.agentsvault.components.AgentsLoadingScreen
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo
import com.gblrod.agentsvault.ui.theme.ButtonAbilityColor
import com.gblrod.agentsvault.viewmodel.AgentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentsScreen(viewModel: AgentViewModel, modifier: Modifier = Modifier) {
    val agents by viewModel.agents.collectAsState()
    var selectAgent by remember { mutableStateOf<AgentDto?>(null) }
    val currentAgent = selectAgent ?: agents.firstOrNull()
    var showAbilitiesSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAgents()
    }

    Box {
        if (agents.isEmpty()) {
            AgentsLoadingScreen()
            return
        }

        currentAgent?.let { agent ->

            Box(
                modifier = modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                BackgroundColorOne,
                                BackgroundColorTwo
                            )
                        )
                    )
                    .fillMaxSize()
            ) {
                if (showAbilitiesSheet) {
                    AgentBottomSheet(
                        agent = agent,
                        sheetState = sheetState,
                        onDismiss = {showAbilitiesSheet = false}
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 165.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = agent.displayName.uppercase(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = agent.role.displayIcon,
                            contentDescription = "Ícone da função",
                            modifier = modifier.size(14.dp),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = agent.role.displayName.uppercase(),
                            fontSize = 13.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (!showAbilitiesSheet) {
                        Button(
                            onClick = { showAbilitiesSheet = true },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(top = 415.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ButtonAbilityColor
                            )
                        ) {
                            Text(
                                text = "Exibir Skills",
                                color = Color.Black
                            )
                        }
                    }
                }
                AsyncImage(
                    model = agent.fullPortrait,
                    contentDescription = "Personagem",
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .align(Alignment.Center)
                        .fillMaxHeight()
                )

                LazyRow(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(17.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(agents) { item ->
                        Card(
                            border = if (currentAgent == item) BorderStroke(
                                width = 2.dp,
                                color = Color.White
                            ) else null,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            AsyncImage(
                                model = item.displayIcon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    //.clip(CircleShape)
                                    .clickable { selectAgent = item }
                            )
                        }
                    }
                }
            }
        }
        AgentSearchBar(
            agents = agents,
            onAgentSelected = { agent ->
                selectAgent = agent
                showAbilitiesSheet = false
            }
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun AgentsScreenPreview() {
//    AgentsScreen(viewModel = AgentViewModel())
//}