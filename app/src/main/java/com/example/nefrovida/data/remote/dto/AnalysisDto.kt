package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisDto(
    @SerializedName("analysis_id") val analysisId: Int,
    @SerializedName("name") val name: String,
)