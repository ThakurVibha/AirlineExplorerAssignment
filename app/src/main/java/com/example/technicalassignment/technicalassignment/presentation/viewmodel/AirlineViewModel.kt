package com.example.technicalassignment.technicalassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.domain.usecase.GetAirlinesUseCase
import com.example.technicalassignment.technicalassignment.utils.ConnectivityObserver
import com.example.technicalassignment.technicalassignment.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel that handles airline data fetching and connectivity status using Hilt and StateFlow.
 */

@HiltViewModel
class AirlineViewModel @Inject constructor(
    private val getAirlinesUseCase: GetAirlinesUseCase,
    private val connectivityObserver: ConnectivityObserver

) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Airline>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline

    private val _airlines = MutableStateFlow<UiState>(UiState.Loading)
    val airlines: StateFlow<UiState> = _airlines

    init {
        observeConnectivity()
        getAirlines()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collectLatest {
                _isOffline.value = it != ConnectivityObserver.Status.Available
            }
        }
    }

    private fun getAirlines() {
        viewModelScope.launch {
            _airlines.value = UiState.Loading
            delay(2000L)
            getAirlinesUseCase().collectLatest { result ->
                _airlines.value = when (result) {
                    is Result.Loading -> UiState.Loading
                    is Result.Success -> UiState.Success(result.data)
                    is Result.Error -> UiState.Error(result.message)
                }
            }
        }
    }

}
