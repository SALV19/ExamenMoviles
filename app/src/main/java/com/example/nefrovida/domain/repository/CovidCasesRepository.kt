package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.CovidCase

interface CovidCasesRepository {
    suspend fun getCovidCasesList(
        country: String?,
        date: String?
    ): List<CovidCase>
}