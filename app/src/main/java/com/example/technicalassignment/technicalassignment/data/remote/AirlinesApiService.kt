package com.example.technicalassignment.technicalassignment.data.remote

import com.example.technicalassignment.technicalassignment.domain.model.Airline
import retrofit2.http.GET

interface AirlinesApiService {
    @GET("airlines.json")
    suspend fun getAirlines(): List<Airline>
}
