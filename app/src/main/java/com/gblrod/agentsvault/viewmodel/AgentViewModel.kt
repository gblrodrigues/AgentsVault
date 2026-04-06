package com.gblrod.agentsvault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.agentsvault.dto.AgentDto
import com.gblrod.agentsvault.dto.MapDto
import com.gblrod.agentsvault.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgentViewModel : ViewModel() {
    private val _agents = MutableStateFlow<List<AgentDto>>(emptyList())
    val agents: StateFlow<List<AgentDto>> = _agents
    private val _maps = MutableStateFlow<List<MapDto>>(emptyList())
    val maps: StateFlow<List<MapDto>> = _maps

    fun fetchAgents() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.findAgents()
                _agents.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchMaps() {
        viewModelScope.launch {
            try {
                val mapResponse = RetrofitInstance.api.findMaps()
                _maps.value = mapResponse.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}