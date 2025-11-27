package com.example.nefrovida.domain.usecase


import com.example.nefrovida.domain.model.CovidCase
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.CovidCasesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCovidCases @Inject constructor(
    private val repository: CovidCasesRepository
) {
    operator fun invoke(
        country: String?,
        date: String?
    ): Flow<Result<List<CovidCase>>> = flow {
        try {
            emit(Result.Loading)
            val pokemonList = repository.getCovidCasesList(
                country,
                date,
            )
            emit(Result.Success(pokemonList))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}