package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.LabAnalysis

interface LabAnalysisRepository {
    suspend fun getLabAnalysisList(): List<LabAnalysis>
}