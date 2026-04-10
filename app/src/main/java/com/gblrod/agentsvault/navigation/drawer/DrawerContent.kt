package com.gblrod.agentsvault.navigation.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gblrod.agentsvault.ui.theme.ThemeViewModel

@Composable
fun DrawerContent(
    navHost: NavHostController,
    onItemClick: () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val currentRoute = navHost.currentBackStackEntryAsState().value?.destination?.route
    var showThemeDialog by remember { mutableStateOf(false) }
    val theme = themeViewModel.theme.collectAsState().value.label
    val items = listOf(
        DrawerItem(
            label = "Agentes",
            route = "main",
            icon = Icons.Default.Groups
        ),
        DrawerItem(
            label = "Agentes Favoritos",
            route = "favorites",
            icon = Icons.Default.Star
        ),
        DrawerItem(
            label = "Mapas",
            route = "maps",
            icon = Icons.Default.Map
        )
    )

    Text(
        text = "Agents Vault",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(16.dp)
    )

    HorizontalDivider()

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "Temas",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Temas",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = theme,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        selected = false,
        onClick = {
            showThemeDialog = true
        }
    )
    if (showThemeDialog) {
        ThemeMenu(
            themeViewModel = themeViewModel,
            onDismiss = {
                showThemeDialog = false
            }
        )
    }
    items.forEach { item ->
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            label = {
                Text(
                    text = item.label,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            selected = currentRoute == item.route,
            onClick = {
                navHost.navigate(item.route) {
                    popUpTo("main") {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                onItemClick()
            }
        )
    }
}