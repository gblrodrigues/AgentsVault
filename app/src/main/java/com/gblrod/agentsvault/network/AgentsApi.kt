package com.gblrod.agentsvault.network

import com.gblrod.agentsvault.dto.AgentResponse
import retrofit2.http.GET

interface AgentsApi {
    @GET("v1/agents")
    suspend fun findAgents(): AgentResponse
}