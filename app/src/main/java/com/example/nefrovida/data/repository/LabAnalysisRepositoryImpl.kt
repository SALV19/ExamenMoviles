package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.LaboratoryApi
import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabAnalysisRepositoryImpl @Inject constructor(
    private val api: LaboratoryApi
) : LabAnalysisRepository {
    override suspend fun getLabAnalysisList(
        page: Int,
        name: String?,
        start: String?,
        end: String?,
        analysis: List<String>?,
        status: String?
    ): List<LabAnalysis> {
        val response = api.getLabResults(
            page = page,
            name = name,
            start = start,
            end = end,
            analysis = analysis,
            status = null
        )
        return response.map { result ->
            result.toDomain()
        }
    }

    override suspend fun getAnalysis(): List<Analysis> {
        val response = api.getLabAnalysis()
        return response.map {result ->
            result.toDomain()
        }
    }
}