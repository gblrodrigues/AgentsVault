package com.gblrod.agentsvault.presentation.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.agentsvault.R
import com.gblrod.agentsvault.ui.theme.ButtonAbilityColor

@Composable
fun EmptyFavorites(
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(id = R.string.cd_favorite_empty_icon),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.favorite_empty_list_title),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.favorite_empty_list_description),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onFavoriteClick()
            },
            modifier = Modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonAbilityColor
            )
        ) {
            Row {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_favorite_back_button_icon),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 7.dp)
                )

                Text(
                    text = stringResource(id = R.string.cd_favorite_back_button),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}