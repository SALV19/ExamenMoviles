package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.CovidCase

@Composable
fun CovidCaseItem(case: CovidCase) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Country: ${case.country}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Region: ${case.region}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Days reported: ${case.cases.dailyCases.size}")
        }
    }
}
