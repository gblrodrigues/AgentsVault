package com.gblrod.agentsvault.components

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.ui.theme.ButtonAbilityColor

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
    onDismissAbilities: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

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
                    contentDescription = "Personagem",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 180.dp)
                        .fillMaxHeight(0.65f)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(540.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = if (agentIsFavorite) Color.Yellow else Color.White
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {

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
                                        color = Color.Black,
                                        modifier = Modifier.align(Alignment.Center)
                                    )

                                    IconToggleButton(
                                        checked = agentIsFavorite,
                                        onCheckedChange = {
                                            onToggleFavorite(agent.uuid)
                                            if (agentIsFavorite) {
                                                Toast.makeText(context,"Agente ${currentAgent.displayName} desfavoritado!", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(context,"Agente ${currentAgent.displayName} favoritado!", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        Icon(
                                            imageVector = if (agentIsFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                            contentDescription = "Ícone de favoritar",
                                            tint = if (agentIsFavorite) Color.Yellow else Color.Black,
                                            modifier = Modifier.size(50.dp)
                                        )
                                    }
                                }

                                Spacer(Modifier.height(4.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = agent.role.displayIcon,
                                        contentDescription = "Ícone da função",
                                        modifier = Modifier.size(14.dp),
                                        colorFilter = ColorFilter.tint(Color.Black)
                                    )

                                    Spacer(Modifier.width(6.dp))

                                    Text(
                                        text = agent.role.displayName.uppercase(),
                                        fontSize = 13.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Button(
                                onClick = onShowAbilities,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ButtonAbilityColor
                                )
                            ) {
                                Text(
                                    text = "Ver Skills",
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = paddingValues.calculateBottomPadding() + 8.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(17.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(agents) { item ->
                        Card(
                            border = if (currentAgent == item) BorderStroke(
                                width = 2.dp,
                                color = Color.White
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
        }
    }
}