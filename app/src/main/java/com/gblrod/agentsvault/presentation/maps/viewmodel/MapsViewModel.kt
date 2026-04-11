package com.gblrod.agentsvault.presentation.maps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.agentsvault.dto.MapDto
import com.gblrod.agentsvault.dto.MapsUiState
import com.gblrod.agentsvault.network.API
import com.gblrod.agentsvault.presentation.retry.viewmodel.RetryViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class MapsViewModel(
    private val api: API
) : ViewModel() {
    private val _mapsUiState = MutableStateFlow<MapsUiState>(MapsUiState.Loading)
    val mapsUiState: StateFlow<MapsUiState> = _mapsUiState
    private var job: Job? = null
    var cachedMaps: List<MapDto> = emptyList()

    init {
        viewModelScope.launch {
            fetchMaps()
        }
    }

    fun observeMapsRetry(retryViewModel: RetryViewModel) {
        viewModelScope.launch {
            retryViewModel.retryEvent.collect {
                fetchMaps()
            }
        }
    }

    fun fetchMaps() {
        job?.cancel()

        job = viewModelScope.launch {
            val firstLoad = _mapsUiState.value is MapsUiState.Loading

            if (firstLoad) {
                _mapsUiState.value = MapsUiState.Loading
            }

            try {
                val mapsResponse = api.findMaps()
                cachedMaps = mapsResponse.data
                _mapsUiState.value = MapsUiState.Success(cachedMaps)

            } catch (e: IOException) {
                if (cachedMaps.isNotEmpty()) {
                    _mapsUiState.value = MapsUiState.Success(cachedMaps)
                } else {
                    _mapsUiState.value =
                        MapsUiState.Error("Erro ao carregar Mapas! \nSem acesso a internet. Verifique sua conexão.")
                }
            } catch (e: HttpException) {
                _mapsUiState.value = MapsUiState.Error("Erro do servidor: ${e.code()}")

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _mapsUiState.value = MapsUiState.Error("Ocorreu um erro inesperado!")
            }
        }
    }
}