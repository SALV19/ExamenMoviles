package com.example.nefrovida.ui.organisms.laboratory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.presentation.screens.labanalysis.AnalysisUIState
import com.example.nefrovida.presentation.screens.labanalysis.LabAnalysisUIState
import com.example.nefrovida.ui.molecule.PopupBox
import com.example.nefrovida.ui.molecule.laboratory.LabAnalysisListContent
import com.example.nefrovida.ui.organisms.ErrorView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LabAnalysisListContainer(
    uiStateLabAnalysis: LabAnalysisUIState,
    analysisList: List<Analysis>,
    onRetry: () -> Unit,
    loadMoreItems: (Int) -> Unit,
    loadAnalysis: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            uiStateLabAnalysis.error != null -> {
                ErrorView(
                    message = uiStateLabAnalysis.error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LabAnalysisListContent(
                    labAnalysisList = uiStateLabAnalysis.labAnalysisList,
                    isLoading = uiStateLabAnalysis.isLoading,
                    hasMore = uiStateLabAnalysis.hasMore,
                    analysisList = analysisList,
                    loadMoreItems = loadMoreItems,
                    loadAnalysis = loadAnalysis,
                )
            }
        }
    }
}