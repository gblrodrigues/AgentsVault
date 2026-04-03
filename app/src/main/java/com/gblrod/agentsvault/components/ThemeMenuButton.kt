package com.gblrod.agentsvault.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gblrod.agentsvault.viewmodel.ThemeViewModel

@Composable
fun ThemeMenuButton(themeViewModel: ThemeViewModel) {
    var showMenuTheme by remember { mutableStateOf(false) }

    Box {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Tema",
            modifier = Modifier
                .size(28.dp)
                .clickable { showMenuTheme = true }
        )

        DropdownMenu(
            expanded = showMenuTheme,
            onDismissRequest = { showMenuTheme = false },
            shape = RoundedCornerShape(12.dp)
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LightMode,
                        contentDescription = "Ícone de tema Light"
                    )
                },
                text = {
                    Text(
                        text = "Claro"
                    )
                },
                onClick = {
                    themeViewModel.setTheme(ThemeOptions.LIGHT)
                    showMenuTheme = false
                }
            )

            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = "Ícone de tema Dark"
                    )
                },
                text = {
                    Text(
                        text = "Escuro"
                    )
                },
                onClick = {
                    themeViewModel.setTheme(ThemeOptions.DARK)
                    showMenuTheme = false
                }
            )

            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.BrightnessHigh,
                        contentDescription = "Ícone de tema do Sistema"
                    )
                },
                text = {
                    Text(
                        text = "Sistema"
                    )
                },
                onClick = {
                    themeViewModel.setTheme(ThemeOptions.SYSTEM)
                    showMenuTheme = false
                }
            )
        }
    }
}