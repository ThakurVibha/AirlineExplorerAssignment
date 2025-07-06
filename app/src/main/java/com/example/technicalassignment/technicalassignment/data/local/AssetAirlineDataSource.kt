package com.example.technicalassignment.technicalassignment.data.local

import android.content.Context
import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.domain.repository.AirlineRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Reads airline data from a local JSON file in assets and exposes it as a Flow.
 */

class AssetAirlineDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : AirlineRepository {
    override fun getAirlines(): Flow<com.example.technicalassignment.technicalassignment.utils.Result<List<Airline>>> =
        flow {
            emit(com.example.technicalassignment.technicalassignment.utils.Result.Loading)
            try {
                val json =
                    context.assets.open("airlines.json").bufferedReader().use { it.readText() }
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val type = Types.newParameterizedType(List::class.java, Airline::class.java)
                val adapter = moshi.adapter<List<Airline>>(type)
                val data = adapter.fromJson(json) ?: emptyList()
                emit(com.example.technicalassignment.technicalassignment.utils.Result.Success(data))
            } catch (e: Exception) {
                emit(com.example.technicalassignment.technicalassignment.utils.Result.Error("Failed to parse assets JSON: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
}