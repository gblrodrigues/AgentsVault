package com.gblrod.agentsvault.presentation.maps.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.agentsvault.dto.MapDto

@Composable
fun MapContent(
    currentMap: MapDto?,
    maps: List<MapDto>,
    selectMap: (MapDto) -> Unit,
    paddingValues: PaddingValues
) {
    val validMaps = remember(maps) {
        maps.filter {
            it.displayName.isNotBlank() && it.displayIcon != null && it.splash != null
        }
    }
    val listState = rememberLazyListState()

    LaunchedEffect(currentMap) {
        currentMap?.let { selected ->
            val index = validMaps.indexOfFirst { it.uuid == selected.uuid }
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = currentMap?.displayIcon,
            contentDescription = "ícone do Mapa (Central)",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 170.dp)
                .fillMaxHeight(0.5f)
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 100.dp)
                    ) {
                        Text(
                            text = currentMap?.displayName?.uppercase() ?: "N/A",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Center)
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
                    bottom = paddingValues.calculateBottomPadding() + 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                items = validMaps,
                key = { it.uuid }
            ) { item ->
                Card(
                    modifier = Modifier
                        .size(width = 180.dp, height = 100.dp)
                        .clickable { selectMap(item) },
                    border = if (currentMap?.uuid == item.uuid) BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.inverseSurface
                    ) else null,
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    AsyncImage(
                        model = item.splash,
                        contentDescription = "Ícone do Mapa (Bottom)",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}