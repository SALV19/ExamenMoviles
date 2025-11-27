package com.example.nefrovida.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.GetCovidCases
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.CovidCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCovidCases: GetCovidCases
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadCovidCases(country: String? = null, date: String? = null) {
        viewModelScope.launch {
            getCovidCases(country, date).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(
                            isLoading = true
                        )

                        is Result.Success -> state.copy(
                            casesList = result.data,
                            isLoading = false,
                            error = null
                        )

                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun loadComparisonData(country1: String, country2: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val combinedList = mutableListOf<CovidCase>()
                var hasError = false
                var errorMessage = ""

                // Load first country
                getCovidCases(country1, null).collect { result1 ->
                    when (result1) {
                        is Result.Loading -> {
                            // Keep loading state
                        }
                        is Result.Success -> {
                            combinedList.addAll(result1.data)
                        }
                        is Result.Error -> {
                            hasError = true
                            errorMessage = "Error loading $country1: ${result1.exception.message}"
                        }
                    }
                }

                // Only continue if first country loaded successfully
                if (!hasError) {
                    // Load second country
                    getCovidCases(country2, null).collect { result2 ->
                        when (result2) {
                            is Result.Loading -> {
                                // Keep loading state
                            }
                            is Result.Success -> {
                                combinedList.addAll(result2.data)
                            }
                            is Result.Error -> {
                                hasError = true
                                errorMessage = "Error loading $country2: ${result2.exception.message}"
                            }
                        }
                    }
                }

                // Update UI state based on results
                if (hasError) {
                    _uiState.update { state ->
                        state.copy(
                            error = errorMessage,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            casesList = combinedList.distinctBy { it.country },
                            isLoading = false,
                            error = null
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        error = e.message ?: "Error loading comparison data",
                        isLoading = false
                    )
                }
            }
        }
    }
}