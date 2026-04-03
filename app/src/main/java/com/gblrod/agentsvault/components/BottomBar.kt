package com.gblrod.agentsvault.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gblrod.agentsvault.navigation.BottomItem

@Composable
fun BottomBar(navHost: NavHostController) {
    val items = listOf(
        BottomItem(
            title = "Home",
            route = "main",
            icon = Icons.Default.Home
        ),

        BottomItem(
            title = "Favoritos",
            route = "favorites",
            icon = Icons.Default.Star
        )
    )
    val currentRoute = navHost.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navHost.navigate(item.route) {
                        popUpTo("main") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                )
            )
        }
    }
}
