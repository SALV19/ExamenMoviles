package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AnalysisDto
import com.example.nefrovida.data.remote.dto.LabAnalysisDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface LaboratoryApi {
    @GET("laboratory/results")
    suspend fun getLabResults(
        @Query("page") page: Int = 0,
        @Query("name") name: String?,
        @Query("start") start: String?,
        @Query("end") end: String?,
        @Query("analysis") analysis: List<String>?,
        @Query("status") status: String?

    ): List<LabAnalysisDto>

    @GET("laboratory/analysis")
    suspend fun getLabAnalysis(): List<AnalysisDto>
}