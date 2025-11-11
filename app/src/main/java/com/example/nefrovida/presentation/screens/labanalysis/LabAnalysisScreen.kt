package com.example.nefrovida.presentation.screens.labanalysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nefrovida.presentation.screens.labanalysis.molecule.LabAnalysisListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabAnalysisScreen(
    viewModel: LabAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Laboratory Analysis")}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            LabAnalysisListContent(
                labAnalysisList = uiState.labAnalysisList,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onRetry = { viewModel.loadLabAnalysisList() }
            )
        }

    }

}