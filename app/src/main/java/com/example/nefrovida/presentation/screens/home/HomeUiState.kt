package com.example.nefrovida.presentation.screens.home

import com.example.nefrovida.domain.model.Cases
import com.example.nefrovida.domain.model.CovidCase

data class HomeUiState(
    val casesList: List<CovidCase> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
