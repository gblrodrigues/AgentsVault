package com.gblrod.agentsvault.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AgentTheme(
    themeOption: ThemeOptions,
    content: @Composable () -> Unit
) {
    val isDarkMode = when (themeOption) {
        ThemeOptions.DARK -> true
        ThemeOptions.LIGHT -> false
        ThemeOptions.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme(),
        content = content
    )
}