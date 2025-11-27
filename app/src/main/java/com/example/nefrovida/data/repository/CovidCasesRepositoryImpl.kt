package com.example.nefrovida.data.repository

import com.example.nefrovida.data.local.preferences.CovidPreferences
import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.CovidApi
import com.example.nefrovida.domain.model.CovidCase
import com.example.nefrovida.domain.repository.CovidCasesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidCasesRepositoryImpl @Inject constructor(
    private val api: CovidApi,
    private val preferences: CovidPreferences

): CovidCasesRepository {
    override suspend fun getCovidCasesList(country: String?, date: String?): List<CovidCase> {
        var selectedCountry: String = "";
        preferences.getCountryCache()?.let { cache ->
            if (!country.isNullOrBlank()) {
                selectedCountry = country
            }
            else if (preferences.isCacheValid()) {
                selectedCountry = cache.country
            }
            else {
                selectedCountry = "canada"
            }
        }

        val response = api.getCases(selectedCountry)
        preferences.saveLastCountry(
            country = selectedCountry,
        )
        return response.map { dto ->
            dto.toDomain()
        }
    }
}