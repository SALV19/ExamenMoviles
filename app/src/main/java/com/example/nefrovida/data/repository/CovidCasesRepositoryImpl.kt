package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.CovidApi
import com.example.nefrovida.domain.model.CovidCase
import com.example.nefrovida.domain.repository.CovidCasesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidCasesRepositoryImpl @Inject constructor(
    private val api: CovidApi
): CovidCasesRepository {
    override suspend fun getCovidCasesList(country: String?, date: String?): List<CovidCase> {
        val response = api.getCases("canada")
        return response.map { dto ->
            dto.toDomain()
        }
    }
}