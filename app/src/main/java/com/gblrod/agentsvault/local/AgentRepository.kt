package com.gblrod.agentsvault.local

import com.gblrod.agentsvault.network.RetrofitInstance

class AgentRepository(
    private val retrofit: RetrofitInstance,
    private val favoriteDataStore: AgentFavoriteDataStore
) {

    suspend fun getAgents() = retrofit.api.findAgents()

    val favoriteAgentes = favoriteDataStore.agentFavoriteFlow

}