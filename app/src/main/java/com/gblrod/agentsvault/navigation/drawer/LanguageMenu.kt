package com.gblrod.agentsvault.navigation.drawer

import android.app.Activity
import androidx.activity.compose.LocalActivity
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
import com.gblrod.agentsvault.language.LanguageOptions
import com.gblrod.agentsvault.language.viewmodel.LanguageViewModel
import com.gblrod.agentsvault.ui.theme.ContainerButtonDialog
import java.util.Locale

@Composable
fun LanguageMenu(
    languageViewModel: LanguageViewModel,
    onDismiss: () -> Unit
) {
    val currentLanguage by languageViewModel.language.collectAsState()

    val deviceLanguage = when (Locale.getDefault().language) {
        "pt" -> LanguageOptions.PT_BR
        "es" -> LanguageOptions.ES
        else -> LanguageOptions.EN_US
    }

    val effectiveLanguage = currentLanguage ?: deviceLanguage

    var selectedLanguage by remember(currentLanguage) {
        mutableStateOf(effectiveLanguage)
    }
    val activity = LocalActivity.current as Activity

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
                text = stringResource(id = R.string.language_dialog_title)
            )
        },
        text = {
            Column {
                LanguageOptions.entries.forEach { language ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedLanguage = language }
                    ) {
                        RadioButton(
                            selected = selectedLanguage == language,
                            onClick = { selectedLanguage = language },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ContainerButtonDialog
                            )
                        )
                        Text(
                            text = stringResource(id = language.label),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedLanguage.let {
                        languageViewModel.setLanguage(it) {
                            activity.recreate()
                        }
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ContainerButtonDialog
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.language_dialog_confirm),
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
                    text = stringResource(id = R.string.language_dialog_dismiss),
                    color = ContainerButtonDialog
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}