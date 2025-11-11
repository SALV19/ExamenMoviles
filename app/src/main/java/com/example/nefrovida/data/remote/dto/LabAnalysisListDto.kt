package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LabAnalysisListDto(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<LabAnalysisDto>
)