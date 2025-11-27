package com.example.nefrovida.domain.model

data class CovidCase(
    val country: String,
    val region: String,
    val cases: Cases
)

data class Cases(
    val dailyCases: Map<String, DailyCase>
)

data class DailyCase(
    val total: Int,
    val newCases: Int
)