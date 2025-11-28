package com.example.nefrovida.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nefrovida.ui.organisms.CovidCaseItem
import com.example.nefrovida.ui.organisms.ComparisonCard
import com.example.nefrovida.ui.organisms.CovidBarChartList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCovidCases(date = null)
    }

    val countries = listOf(
        "Mexico",
        "Canada",
        "Brazil",
        "Japan",
        "Spain",
        "France",
        "Germany",
        "Italy",
        "United Kingdom",
        "Argentina",
        "Australia",
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }

    // Date filter states
    var dateExpanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    // Comparison mode states
    var isComparisonMode by remember { mutableStateOf(false) }
    var country1Expanded by remember { mutableStateOf(false) }
    var country2Expanded by remember { mutableStateOf(false) }
    var selectedCountry1 by remember { mutableStateOf("") }
    var selectedCountry2 by remember { mutableStateOf("") }

    // Get available dates from the loaded data (last 20 only)
    val availableDates = remember(uiState.casesList) {
        uiState.casesList
            .flatMap { it.cases.dailyCases.keys }
            .distinct()
            .sortedDescending()
            .take(20)
    }

    // Filtered cases by date
    val filteredCases = remember(uiState.casesList, selectedDate) {
        if (selectedDate.isEmpty()) {
            uiState.casesList
        } else {
            uiState.casesList.map { covidCase ->
                val filteredDailyCases = covidCase.cases.dailyCases
                    .filterKeys { it == selectedDate }

                covidCase.copy(
                    cases = covidCase.cases.copy(
                        dailyCases = filteredDailyCases.ifEmpty {
                            emptyMap()
                        }
                    )
                )
            }.filter { it.cases.dailyCases.isNotEmpty() }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Gradient Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isComparisonMode) 320.dp else 280.dp)
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
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🦠 COVID-19 Tracker",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = if (isComparisonMode) "Compare countries" else "Real-time global statistics",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.9f)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Toggle comparison mode button
                IconButton(
                    onClick = {
                        isComparisonMode = !isComparisonMode
                        if (!isComparisonMode) {
                            selectedCountry1 = ""
                            selectedCountry2 = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isComparisonMode) Color.White.copy(alpha = 0.3f)
                            else Color.White.copy(alpha = 0.2f)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Compare,
                        contentDescription = "Compare",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isComparisonMode) {
                // Comparison Mode UI
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Compare,
                                contentDescription = null,
                                tint = Color(0xFF667eea),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Compare Two Countries",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        // Country 1 Dropdown
                        Text(
                            text = "First Country",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        ExposedDropdownMenuBox(
                            expanded = country1Expanded,
                            onExpandedChange = { country1Expanded = !country1Expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedCountry1.ifEmpty { "Select first country" },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = country1Expanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF667eea),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = country1Expanded,
                                onDismissRequest = { country1Expanded = false }
                            ) {
                                countries.forEach { country ->
                                    DropdownMenuItem(
                                        text = { Text(country) },
                                        onClick = {
                                            selectedCountry1 = country
                                            country1Expanded = false
                                        },
                                        enabled = country != selectedCountry2
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // VS Divider
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalDivider(color = Color(0xFFE0E0E0))
                            Text(
                                text = "VS",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF667eea)
                                ),
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(horizontal = 12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Country 2 Dropdown
                        Text(
                            text = "Second Country",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        ExposedDropdownMenuBox(
                            expanded = country2Expanded,
                            onExpandedChange = { country2Expanded = !country2Expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedCountry2.ifEmpty { "Select second country" },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = country2Expanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF764ba2),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = country2Expanded,
                                onDismissRequest = { country2Expanded = false }
                            ) {
                                countries.forEach { country ->
                                    DropdownMenuItem(
                                        text = { Text(country) },
                                        onClick = {
                                            selectedCountry2 = country
                                            country2Expanded = false
                                        },
                                        enabled = country != selectedCountry1
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Compare Button
                        Button(
                            onClick = {
                                if (selectedCountry1.isNotEmpty() && selectedCountry2.isNotEmpty()) {
                                    viewModel.loadComparisonData(selectedCountry1, selectedCountry2)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = selectedCountry1.isNotEmpty() && selectedCountry2.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF667eea)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Compare,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Compare Countries")
                        }
                    }
                }
            } else {
                // Normal Filters Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                tint = Color(0xFF667eea),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Filters",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Text(
                            text = "Country",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedCountry.ifEmpty { "All Countries" },
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Choose a country") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF667eea),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "All Countries",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF667eea)
                                            )
                                        )
                                    },
                                    onClick = {
                                        selectedCountry = ""
                                        expanded = false
                                        viewModel.loadCovidCases("", null)
                                    }
                                )

                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                                countries.forEach { country ->
                                    DropdownMenuItem(
                                        text = { Text(country) },
                                        onClick = {
                                            selectedCountry = country
                                            expanded = false
                                            viewModel.loadCovidCases(country, null)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF764ba2),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Date",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                        }

                        ExposedDropdownMenuBox(
                            expanded = dateExpanded,
                            onExpandedChange = { dateExpanded = !dateExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedDate.ifEmpty { "All Dates (Latest)" },
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Choose a date") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dateExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF764ba2),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = availableDates.isNotEmpty(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = dateExpanded,
                                onDismissRequest = { dateExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "All Dates (Latest)",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF764ba2)
                                            )
                                        )
                                    },
                                    onClick = {
                                        selectedDate = ""
                                        dateExpanded = false
                                    }
                                )

                                if (availableDates.isNotEmpty()) {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                                    availableDates.forEach { date ->
                                        DropdownMenuItem(
                                            text = {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(date)
                                                    if (date == availableDates.first()) {
                                                        Text(
                                                            "Latest",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = Color(0xFF4CAF50)
                                                        )
                                                    }
                                                }
                                            },
                                            onClick = {
                                                selectedDate = date
                                                dateExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if (selectedCountry.isNotEmpty() || selectedDate.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            TextButton(
                                onClick = {
                                    selectedCountry = ""
                                    selectedDate = ""
                                    viewModel.loadCovidCases("", null)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Clear All Filters",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF667eea)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Content based on mode
            if (isComparisonMode && selectedCountry1.isNotEmpty() && selectedCountry2.isNotEmpty()) {
                // Comparison Mode View
                if (!uiState.isLoading && uiState.error == null) {
                    Text(
                        text = "Comparison Results",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                val country1Data = uiState.casesList.find {
                    it.country.equals(selectedCountry1, ignoreCase = true)
                }
                val country2Data = uiState.casesList.find {
                    it.country.equals(selectedCountry2, ignoreCase = true)
                }

                if (country1Data != null && country2Data != null) {
                    ComparisonCard(
                        country1 = country1Data,
                        country2 = country2Data
                    )
                } else if (!uiState.isLoading && uiState.error == null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF9C4)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⏳ Click 'Compare Countries' to load data",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFF57F17)
                            )
                        }
                    }
                }
            } else {
                // Normal List View
                if (!uiState.isLoading && uiState.error == null && filteredCases.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Latest Statistics",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        if (selectedDate.isNotEmpty()) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF0F4FF)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = selectedDate,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color(0xFF667eea),
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                if (!uiState.isLoading && uiState.error == null && filteredCases.isEmpty() && selectedDate.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF9C4)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "📅",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No data for selected date",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = Color(0xFFF57F17)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Try selecting a different date",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFF57F17)
                                )
                            }
                        }
                    }
                }

                (CovidBarChartList(filteredCases))
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filteredCases) { covidCase ->
//                        CovidCaseItem(covidCase)
//                    }
//
//                    item {
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
//                }
            }

            // Loading State
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = Color(0xFF667eea),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading data...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Error State
            uiState.error?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚠️ Error",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Error retrieving information",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFC62828)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadCovidCases(country = "", date = null) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF667eea)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}