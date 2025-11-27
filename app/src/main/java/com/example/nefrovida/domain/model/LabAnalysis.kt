package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.AnalysisStatus
import java.util.Date

data class LabAnalysis(
    val patientAnalysisId: Int,
    val analysisDate: Date,
    val resultsDate: Date,
    val analysisStatus: AnalysisStatus,

    val name: String,
    val parentLastName: String,
    val maternalLastName: String,
) {
}