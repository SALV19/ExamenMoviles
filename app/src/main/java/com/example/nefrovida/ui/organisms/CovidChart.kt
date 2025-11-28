package com.example.nefrovida.ui.organisms

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.domain.model.CovidCase

@Composable
fun CovidBarChartList(cases: List<CovidCase>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(cases) { case ->
            CovidBarChartItem(case, maxCases = cases.maxOf {
                it.cases.dailyCases.entries.lastOrNull()?.value?.total ?: 1
            })
        }
    }
}


@Composable
fun CovidBarChartItem(case: CovidCase, maxCases: Int) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400),
        label = ""
    )

    val totalCases = case.cases.dailyCases.entries.lastOrNull()?.value?.total ?: 0
    val percentage = (totalCases.toFloat() / maxCases).coerceIn(0f, 1f)

    val barColor = when {
        totalCases > 1_000_000 -> Color(0xFFEF5350) // Red
        totalCases > 100_000 -> Color(0xFFFFA726)  // Orange
        else -> Color(0xFF66BB6A)                 // Green
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .alpha(alpha)
    ) {
        // Country + total number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Flag
                Text(
                    text = getCountryFlag(case.country),
                    fontSize = 24.sp,
                )

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = case.country,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (case.region.isNotEmpty()) {
                        Text(
                            text = case.region,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Text(
                text = formatNumber(totalCases),
                style = MaterialTheme.typography.titleMedium,
                color = barColor
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Background bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFF0F0F0))
        ) {
            // Filled bar indicating percentage
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(barColor)
            )
        }
    }
}

