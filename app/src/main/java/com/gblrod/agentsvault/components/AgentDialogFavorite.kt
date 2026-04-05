package com.gblrod.agentsvault.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.gblrod.agentsvault.ui.theme.ContainerButtonDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentDialogFavorite(
    title: String,
    description: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
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
        title = {
            Text(
                text = title
            )
        },
        text = {
            Text(
                text = description
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ContainerButtonDialog
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Confirmar",
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
                    text = "Cancelar",
                    color = ContainerButtonDialog
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}