package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.LabAnalysisDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LaboratoryApi {
    @GET("laboratory/results")
    suspend fun getLabResults(
        @Query("page") page: Int = 0
    ): List<LabAnalysisDto>
}