package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import com.example.nefrovida.domain.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnalysisUseCase @Inject constructor(
    private val repository: LabAnalysisRepository
) {
    operator fun invoke(): Flow<Result<List<Analysis>>> = flow {
        try {
            emit(Result.Loading)
            val analysisList = repository.getAnalysis()
            emit(Result.Success(analysisList))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}