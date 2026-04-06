package com.gblrod.agentsvault.network

import com.gblrod.agentsvault.dto.AgentResponse
import com.gblrod.agentsvault.dto.MapResponse
import retrofit2.http.GET

interface API {
    @GET("v1/agents")
    suspend fun findAgents(): AgentResponse

    @GET("v1/maps")
    suspend fun findMaps(): MapResponse
}