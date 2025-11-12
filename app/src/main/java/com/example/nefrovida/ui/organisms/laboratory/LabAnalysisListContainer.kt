package com.example.nefrovida.ui.organisms.laboratory

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
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.ui.molecule.PopupBox
import com.example.nefrovida.ui.molecule.laboratory.LabAnalysisListContent
import com.example.nefrovida.ui.organisms.ErrorView

@Composable
fun LabAnalysisListContainer(
    labAnalysisList: List<LabAnalysis>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    loadMoreItems: (Int) -> Unit
) {
    var showFilter by rememberSaveable { mutableStateOf(false) }

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
                LabAnalysisListContent(
                    labAnalysisList = labAnalysisList,
                    loadMoreItems = loadMoreItems
                )
            }
        }
    }

    PopupBox(
        popupWidth = 200F,
        popupHeight = 300F,
        showPopup = showFilter,
        onClickOutside = {showFilter = false}
    ) {
        Text("Filter")
    }
}