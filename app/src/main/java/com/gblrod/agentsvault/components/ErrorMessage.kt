package com.gblrod.agentsvault.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gblrod.agentsvault.presentation.retry.RetryViewModel
import kotlinx.coroutines.delay

@Composable
fun ErrorMessage(
    message: String,
    retryViewModel: RetryViewModel
) {
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            LaunchedEffect(Unit) {
                delay(15_000)
                isLoading = false
            }
            CircularProgressIndicator(
                color = Color.Black
            )
        } else {
            TextButton(
                onClick = {
                    isLoading = true
                    retryViewModel.retryAll()
                },
            ) {
                Text(
                    text = "TENTAR NOVAMENTE",
                    color = Color.Black
                )
            }
        }
    }
}