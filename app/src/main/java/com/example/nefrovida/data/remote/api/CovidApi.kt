package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.CovidCaseDto
import retrofit2.http.Query
import retrofit2.http.GET

interface CovidApi {
    @GET("/")
    suspend fun getCases(
        @Query("country") country: String = "canada",
        @Query("date") date: String?
    ): CovidCaseDto
}