package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.model.LabAnalysis
import java.time.LocalDateTime

interface LabAnalysisRepository {
    suspend fun getLabAnalysisList(
        page: Int,
        name: String? = null, // "Sofía"
        start: String? = null, // "2025-11-11T00:00:00.000Z"
        end: String? = null, // "2025-11-11T00:00:00.000Z"
        analysis: List<String>? = null,
        status: String? = null
    ): List<LabAnalysis>
    suspend fun getAnalysis(): List<Analysis>
}