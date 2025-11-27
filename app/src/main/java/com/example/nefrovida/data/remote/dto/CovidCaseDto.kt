package com.example.nefrovida.data.remote.dto

import com.example.nefrovida.domain.model.DailyCase
import com.google.gson.annotations.SerializedName

data class CovidCaseListDto(
    val results: List<CovidCaseDto>
)

data class CovidCaseDto(
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("cases") val cases: Map<String, DailyCaseDto>
)

data class DailyCaseDto(
    val total: Int,
    @SerializedName("new") val newCases: Int
)
