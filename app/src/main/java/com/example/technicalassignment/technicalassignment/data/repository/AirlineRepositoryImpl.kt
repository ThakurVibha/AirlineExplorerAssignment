package com.example.technicalassignment.technicalassignment.data.repository

import com.example.technicalassignment.technicalassignment.data.remote.AirlinesApiService
import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.domain.repository.AirlineRepository
import com.example.technicalassignment.technicalassignment.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Repository implementation that fetches airline data from a remote API using Retrofit.
 */

class AirlineRepositoryImpl @Inject constructor(
    private val apiService: AirlinesApiService
) : AirlineRepository {

    override fun getAirlines(): Flow<Result<List<Airline>>> = flow {
        emit(Result.Loading)
        try {
            val data = apiService.getAirlines()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}
