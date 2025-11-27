package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.CovidCaseDto
import com.example.nefrovida.data.remote.dto.CovidCaseListDto
import com.example.nefrovida.data.remote.dto.DailyCaseDto
import com.example.nefrovida.domain.model.Cases
import com.example.nefrovida.domain.model.CovidCase
import com.example.nefrovida.domain.model.DailyCase

fun CovidCaseListDto.toDomain(): List<CovidCase> {
    return results.map { it.toDomain() }
}

fun CovidCaseDto.toDomain(): CovidCase {
    return CovidCase(
        country = country,
        region = region,
        cases = Cases(cases.mapValues { it.value.toDomain() })
    )
}

fun DailyCaseDto.toDomain(): DailyCase {
    return DailyCase(
        total = total,
        newCases = newCases
    )
}
