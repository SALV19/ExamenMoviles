package com.example.nefrovida.presentation.screens.labanalysis.molecule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.presentation.screens.labanalysis.LabAnalysisViewModel
import com.example.nefrovida.presentation.screens.labanalysis.atoms.LabAnalysisCard
import com.example.nefrovida.ui.organism.ErrorView

@Composable
fun LabAnalysisListContent(
    labAnalysisList: List<LabAnalysis>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            error != null -> {

                ErrorView(
                    message = error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(labAnalysisList) { labAnalysis ->
                        LabAnalysisCard(labAnalysis)
                    }
                }
            }
        }
    }
}