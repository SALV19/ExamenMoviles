package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AnalysisDto
import com.example.nefrovida.domain.model.Analysis

fun AnalysisDto.toDomain(): Analysis {
    return Analysis(
        analysisId = analysisId,
        name = name
    )
}