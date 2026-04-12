package com.gblrod.agentsvault.navigation.drawer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.gblrod.agentsvault.R
import com.gblrod.agentsvault.presentation.theme.ThemeOptions
import com.gblrod.agentsvault.presentation.theme.viewmodel.ThemeViewModel
import com.gblrod.agentsvault.ui.theme.ContainerButtonDialog

@Composable
fun ThemeMenu(
    themeViewModel: ThemeViewModel,
    onDismiss: () -> Unit
) {
    val currentTheme by themeViewModel.theme.collectAsState()
    var selectedTheme by remember { mutableStateOf(currentTheme) }

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(0.80f)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(16.dp)
            ),
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.theme_dialog_title)
            )
        },
        text = {
            Column {
                ThemeOptions.entries.forEach { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTheme = theme }
                    ) {
                        RadioButton(
                            selected = selectedTheme == theme,
                            onClick = {  },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ContainerButtonDialog
                            )
                        )
                        Text(
                            text = theme.label,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    themeViewModel.setTheme(selectedTheme!!)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ContainerButtonDialog
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.theme_dialog_confirm),
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = ContainerButtonDialog
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.theme_dialog_dismiss),
                    color = ContainerButtonDialog
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}