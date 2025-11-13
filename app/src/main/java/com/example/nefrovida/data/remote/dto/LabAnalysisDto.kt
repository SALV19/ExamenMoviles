package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

enum class AnalysisStatus {
    SENT,
    PENDING,
    LAB,
    REQUESTED
}

data class LabAnalysisDto(
    @SerializedName("patient_analysis_id") val patientAnalysisId: Int,
    @SerializedName("analysis_date") val analysisDate: Date,
    @SerializedName("results_date") val resultsDate: Date,
    @SerializedName("analysis_status") val analysisStatus: AnalysisStatus,
    @SerializedName("patient") val patient: PatientDto
) {
    data class PatientDto(
        @SerializedName("user") val user: UserDto
    )

    data class UserDto(
        @SerializedName("name") val name: String,
        @SerializedName("parent_last_name") val parentLastName: String,
        @SerializedName("maternal_last_name") val maternalLastName: String
    )
}