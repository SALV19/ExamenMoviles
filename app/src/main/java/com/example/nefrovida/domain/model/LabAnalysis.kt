package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.AnalysisTypes
import java.util.Date

data class LabAnalysis(
    val patient_analysis_id: Int,
    val analysis_date: Date,
    val results_date: Date,
    val analysis_status: AnalysisTypes,

    val name: String,
    val parent_last_name: String,
    val maternal_last_name: String,
) {
}