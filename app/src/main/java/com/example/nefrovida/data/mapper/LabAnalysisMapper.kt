package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.LabAnalysisDto
import com.example.nefrovida.domain.model.LabAnalysis

fun LabAnalysisDto.toDomain(): LabAnalysis {
    return LabAnalysis(
        patientAnalysisId,
        analysisDate,
        resultsDate,
        analysisStatus,
        name = patient.user.name,
        parentLastName = patient.user.parentLastName,
        maternalLastName = patient.user.maternalLastName,
    )
}