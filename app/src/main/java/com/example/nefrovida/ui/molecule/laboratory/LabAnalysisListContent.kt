package com.example.nefrovida.ui.molecule.laboratory

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.ui.atoms.ClickableIcon
import com.example.nefrovida.ui.atoms.Pill
import com.example.nefrovida.ui.atoms.Title
import com.example.nefrovida.ui.atoms.laboratory.LabAnalysisCard
import com.example.nefrovida.ui.molecule.PopupBox
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LabAnalysisListContent(
    labAnalysisList: List<LabAnalysis>,
    isLoading: Boolean,
    hasMore: Boolean,
    analysisList: List<Analysis>,
    loadMoreItems: (Int) -> Unit,
    loadAnalysis: () -> Unit
) {
    val scrollState = rememberLazyListState()
    var page by remember { mutableIntStateOf(1) }
    var showFilter by rememberSaveable { mutableStateOf(false) }
    var showSearch by rememberSaveable { mutableStateOf(false) }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = scrollState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItems - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoading) {
            loadMoreItems(page)
            page += 1
        }
    }
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        when (isLoading && labAnalysisList.isEmpty()) {
            true -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Title("Resultados de laboratorio")
                    ClickableIcon(
                        onClick = { showSearch = true },
                        icon = Icons.Outlined.Search
                    )
                    ClickableIcon(
                        onClick = { showFilter = true },
                        icon = Icons.Outlined.FilterAlt
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    state = scrollState
                ) {
                    itemsIndexed(labAnalysisList) { idx, labAnalysis ->
                        LabAnalysisCard(labAnalysis)
                    }

                    if (isLoading && !hasMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }

    PopupBox(
        popupWidth = 500F,
        popupHeight = 800F,
        showPopup = showFilter,
        onClickOutside = { showFilter = false }
    ) {
        LabAnalysisFilter(
            analysisList = analysisList,
            onChange = { start, end, list, status ->
                Log.d("MyActivity", start.toString())
                Log.d("MyActivity", end.toString())
                Log.d("MyActivity", list.toString())
                Log.d("MyActivity", status.toString())
            },
            onClose = { showFilter = false },
            loadAnalysis = loadAnalysis
        )
    }

}