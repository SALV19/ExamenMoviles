package com.example.nefrovida.presentation.screens.labanalysis

import com.example.nefrovida.domain.model.LabAnalysis

data class LabAnalysisUIState(
    val labAnalysisList: List<LabAnalysis> = emptyList(),
    val hasMore: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)