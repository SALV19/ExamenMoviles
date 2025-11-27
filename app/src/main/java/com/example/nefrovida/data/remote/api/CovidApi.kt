package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.CovidCaseDto
import retrofit2.http.Query
import retrofit2.http.GET

interface CovidApi {
    @GET("covid19")
    suspend fun getCases(
        @Query("country") country: String,
//        @Query("date") date: String?
    ): List<CovidCaseDto>

}