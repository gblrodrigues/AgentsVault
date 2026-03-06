package com.gblrod.agentsvault.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.gblrod.agentsvault.dto.AgentDto
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.gblrod.agentsvault.ui.theme.BackGroundCardColorOne
import com.gblrod.agentsvault.ui.theme.BackGroundCardColorTwo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentBottomSheet(
    agent: AgentDto,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    val scroll = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.Transparent,
        scrimColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .verticalScroll(scroll)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            BackGroundCardColorOne,
                            BackGroundCardColorTwo
                        )
                    )
                )
                .padding(20.dp)
        ) {

            Column {
                agent.abilities.forEach {
                    Row {
                        AsyncImage(
                            model = it.displayIcon,
                            contentDescription = "Ícones das habilidades",
                            modifier = Modifier.size(45.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = it.displayName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it.description,
                                fontSize = 13.sp,
                                color = Color.White,
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}