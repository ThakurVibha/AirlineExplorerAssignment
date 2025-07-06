package com.example.technicalassignment.technicalassignment.domain.repository

import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.utils.Result
import kotlinx.coroutines.flow.Flow

interface AirlineRepository {
    fun getAirlines(): Flow<Result<List<Airline>>>
}
