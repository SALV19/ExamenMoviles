package com.example.nefrovida.data.remote.dto

import com.example.nefrovida.domain.model.DailyCase
import com.google.gson.annotations.SerializedName

data class CovidCaseListDto(
    @SerializedName("results") val results: List<CovidCaseDto>
)

data class CovidCaseDto(
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("cases") val cases: CasesDto
)

data class CasesDto(
    @SerializedName(value = "cases")
        val dailyCases: Map<String, DailyCaseDto>
)

data class DailyCaseDto(
    @SerializedName("total") val total: Int,
    @SerializedName("new") val newCases: Int
)
