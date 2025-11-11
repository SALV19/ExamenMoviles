package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.LabAnalysisDto
import com.example.nefrovida.domain.model.LabAnalysis

fun LabAnalysisDto.toDomain(): LabAnalysis {
    return LabAnalysis(
        patient_analysis_id = patient_analysis_id,
        analysis_date = analysis_date,
        analysis_status = analysis_status,
        results_date = results_date,
        name = patient.user.name,
        parent_last_name = patient.user.parent_last_name,
        maternal_last_name = patient.user.maternal_last_name,
    )
}