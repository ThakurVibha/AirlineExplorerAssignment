package com.example.technicalassignment.technicalassignment.domain.usecase

import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.domain.repository.AirlineRepository
import com.example.technicalassignment.technicalassignment.utils.Result
import kotlinx.coroutines.flow.Flow

class GetAirlinesUseCase(
    private val repository: AirlineRepository
) {
    operator fun invoke(): Flow<Result<List<Airline>>> {
        return repository.getAirlines()
    }
}
