package com.gblrod.agentsvault.presentation.agentsvault

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gblrod.agentsvault.R
import com.gblrod.agentsvault.ui.theme.BackgroundColorOne
import com.gblrod.agentsvault.ui.theme.BackgroundColorTwo

@Composable
fun AgentsLoadingScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Fundo do Aplicativo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = "Carregando...",
            color = Color.Black,
            modifier = Modifier.padding(top = 80.dp)
        )
        CircularProgressIndicator(
             color = Color.Black
        )
    }
}