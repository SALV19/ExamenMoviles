package com.example.nefrovida.ui.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.nefrovida.ui.theme.SpecialBlue

@Composable
fun Title(
    text: String
) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        color = SpecialBlue
    )
}