package com.example.nefrovida.ui.organisms

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.domain.model.CovidCase

@Composable
fun ComparisonCard(
    country1: CovidCase,
    country2: CovidCase
) {
    val country1Data = country1.cases.dailyCases.entries.lastOrNull()?.value
    val country2Data = country2.cases.dailyCases.entries.lastOrNull()?.value

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header with VS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Country 1
                CountryHeader(
                    country = country1.country,
                    color = Color(0xFF667eea),
                    modifier = Modifier.weight(1f)
                )

                // VS Badge
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VS",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                }

                // Country 2
                CountryHeader(
                    country = country2.country,
                    color = Color(0xFF764ba2),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Total Cases Comparison
            ComparisonRow(
                label = "Total Cases",
                icon = "🦠",
                value1 = country1Data?.total ?: 0,
                value2 = country2Data?.total ?: 0,
                color = Color(0xFFFF6B6B)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(16.dp))

            // Deaths Comparison
            ComparisonRow(
                label = "New Cases",
                icon = "🦠",
                value1 = country1Data?.newCases ?: 0,
                value2 = country2Data?.newCases ?: 0,
                color = Color(0xFF4A5568)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Divider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(20.dp))

            // Recovery Rate Comparison
            val recovery1 = calculateRecoveryRate(country1)
            val recovery2 = calculateRecoveryRate(country2)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RecoveryRateCard(
                    country = country1.country,
                    rate = recovery1,
                    color = Color(0xFF667eea),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                RecoveryRateCard(
                    country = country2.country,
                    rate = recovery2,
                    color = Color(0xFF764ba2),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Winner Badge
            val winner = when {
                recovery1 > recovery2 -> country1.country
                recovery2 > recovery1 -> country2.country
                else -> null
            }

            if (winner != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF0F4FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "🏆", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$winner has better recovery rate",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF667eea)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountryHeader(
    country: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getCountryFlag(country),
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = country,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ComparisonRow(
    label: String,
    icon: String,
    value1: Int,
    value2: Int,
    color: Color
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.Gray
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Value 1
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (value1 > value2) color.copy(alpha = 0.2f)
                        else Color(0xFFF5F5F5)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatNumber(value1),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (value1 > value2) color else Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Value 2
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (value2 > value1) color.copy(alpha = 0.2f)
                        else Color(0xFFF5F5F5)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatNumber(value2),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (value2 > value1) color else Color.Gray
                    )
                )
            }
        }

        // Difference indicator
        if (value1 != value2) {
            val difference = kotlin.math.abs(value1 - value2)
            val percentage = if (kotlin.math.max(value1, value2) > 0) {
                (difference.toFloat() / kotlin.math.max(value1, value2) * 100).toInt()
            } else 0

            Text(
                text = "↔ ${formatNumber(difference)} difference ($percentage%)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun RecoveryRateCard(
    country: String,
    rate: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recovery Rate",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${rate.toInt()}%",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(rate / 100f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(color)
                )
            }
        }
    }
}