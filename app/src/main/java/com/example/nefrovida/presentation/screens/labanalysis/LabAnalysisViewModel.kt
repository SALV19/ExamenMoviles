package com.example.nefrovida.presentation.screens.labanalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.GetLabAnalysisListUseCase
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.GetAnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class LabAnalysisViewModel @Inject constructor(
    private val getLabAnalysisListUseCase: GetLabAnalysisListUseCase,
    private val getAnalysisUseCase: GetAnalysisUseCase
): ViewModel(){
    private val _uiStateLabAnalysisList = MutableStateFlow(LabAnalysisUIState())
    val uiStateLabAnalysis: StateFlow<LabAnalysisUIState> = _uiStateLabAnalysisList.asStateFlow()

    private val _uiStateAnalysisList = MutableStateFlow(AnalysisUIState())
    val uiStateAnalysisList: StateFlow<AnalysisUIState> = _uiStateAnalysisList.asStateFlow()

    init {
        loadLabAnalysisList()
    }

    fun loadLabAnalysisList(page: Int = 0) {
        viewModelScope.launch {
            getLabAnalysisListUseCase(page).collect { result ->
                _uiStateLabAnalysisList.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(
                            isLoading = true,
                        )
                        is Result.Success -> state.copy(
                            labAnalysisList = if (page == 0) {
                              result.data
                            } else {
                                state.labAnalysisList + result.data
                            },
                            isLoading = false,
                            hasMore = if (result.data.size == 0) {
                                false
                            } else {
                                state.hasMore
                            },
                            error = null,
                        )
                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    fun getAnalysisList() {
        viewModelScope.launch {
            getAnalysisUseCase().collect { result ->
                _uiStateAnalysisList.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(
                            isLoading = true,
                        )
                        is Result.Success -> state.copy(
                            analysisList = result.data,
                            isLoading = false,
                            error = null,
                        )
                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}