package com.example.technicalassignment.technicalassignment.di

import android.content.Context
import com.example.technicalassignment.technicalassignment.data.local.AssetAirlineDataSource
import com.example.technicalassignment.technicalassignment.domain.repository.AirlineRepository
import com.example.technicalassignment.technicalassignment.domain.usecase.GetAirlinesUseCase
import com.example.technicalassignment.technicalassignment.utils.ConnectivityObserver
import com.example.technicalassignment.technicalassignment.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides app-level dependencies such as
 * repository, use case, and connectivity observer.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAirlineRepository(
        @ApplicationContext context: Context
    ): AirlineRepository = AssetAirlineDataSource(context)

    @Provides
    @Singleton
    fun provideGetAirlinesUseCase(
        repository: AirlineRepository
    ): GetAirlinesUseCase = GetAirlinesUseCase(repository)

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}
