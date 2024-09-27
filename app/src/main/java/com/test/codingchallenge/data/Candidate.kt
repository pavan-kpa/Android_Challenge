package com.test.codingchallenge.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://personio-fe-coding-challenge.vercel.app/api/"

data class Candidate (
    var id: Int,
    var name: String,
    var email: String,
    @SerializedName("birth_date") var birthDate: String,  // Format: MM/dd/yyyy
    @SerializedName("position_applied") var positionApplied: String,
    @SerializedName("application_date") var applicationDate: String,  // Format: MM/dd/yyyy
    @SerializedName("year_of_experience") var yearsOfExperience: Int,
    var status: String,  // One of: approved, rejected, waiting
)

data class CandidateResponse (
    @SerializedName("data") val data: List<Candidate>,
)

interface CandidateService {
    @GET("candidates")
    suspend fun getCandidates(): CandidateResponse

    companion object {
        private var candidateService: CandidateService? = null
        fun getInstance(): CandidateService {
            if (candidateService == null) {
                candidateService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .build().create(CandidateService::class.java)
            }
            return candidateService!!
        }
    }
}
