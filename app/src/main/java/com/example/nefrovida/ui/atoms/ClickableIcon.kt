package com.example.nefrovida.ui.atoms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ClickableIcon(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            Color.Transparent,
            Color.Black,
            Color.Transparent),
    ) {
        Icon(
            icon,
            contentDescription = null,
        )
    }
}