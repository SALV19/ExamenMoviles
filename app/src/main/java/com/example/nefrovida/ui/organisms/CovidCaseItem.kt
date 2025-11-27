package com.example.nefrovida.ui.organisms

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.domain.model.CovidCase

@Composable
fun CovidCaseItem(case: CovidCase) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "alpha"
    )

    val totalCases = case.cases.dailyCases.entries.lastOrNull()?.value?.total ?: 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            // Gradient accent bar on the left
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF667eea),
                                Color(0xFF764ba2)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                // Header Row with Country and Flag Emoji
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getCountryFlag(case.country),
                                fontSize = 24.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = case.country,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
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
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatBox(
                        label = "Total Cases",
                        value = formatNumber(totalCases),
                        color = Color(0xFFFF6B6B),
                        icon = "🦠"
                    )

                    StatBox(
                        label = "Active",
                        value = formatNumber(case.cases.dailyCases.entries.lastOrNull()?.value?.total ?: 0),
                        color = Color(0xFFFFAB40),
                        icon = "⚠️"
                    )

                    StatBox(
                        label = "New",
                        value = formatNumber(case.cases.dailyCases.entries.lastOrNull()?.value?.newCases ?: 0),
                        color = Color(0xFF4A5568),
                        icon = "\uD83E\uDDA0"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Recovery Rate Bar
                val recoveryRate = calculateRecoveryRate(case)
                if (recoveryRate > 0) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Recovery Rate",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = "${recoveryRate}%",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF5F5F5))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(recoveryRate / 100f)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF4CAF50),
                                                Color(0xFF8BC34A)
                                            )
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(
    label: String,
    value: String,
    color: Color,
    icon: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format("%.1fK", number / 1_000.0)
        else -> number.toString()
    }
}

fun calculateRecoveryRate(case: CovidCase): Float {
    val lastEntry = case.cases.dailyCases.entries.lastOrNull()?.value
    val total = lastEntry?.total ?: 0
    val deaths = lastEntry?.newCases ?: 0
    val active = lastEntry?.total ?: 0

    if (total == 0) return 0f

    val recovered = total - deaths - active
    return ((recovered.toFloat() / total) * 100).coerceIn(0f, 100f)
}

fun getCountryFlag(country: String): String {
    return when (country.lowercase()) {
        "united states", "usa" -> "🇺🇸"
        "mexico" -> "🇲🇽"
        "canada" -> "🇨🇦"
        "brazil" -> "🇧🇷"
        "japan" -> "🇯🇵"
        "spain" -> "🇪🇸"
        "france" -> "🇫🇷"
        "germany" -> "🇩🇪"
        "italy" -> "🇮🇹"
        "united kingdom", "uk" -> "🇬🇧"
        "china" -> "🇨🇳"
        "india" -> "🇮🇳"
        "russia" -> "🇷🇺"
        "australia" -> "🇦🇺"
        else -> "🌍"
    }
}