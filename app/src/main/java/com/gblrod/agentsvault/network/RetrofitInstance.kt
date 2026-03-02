package com.gblrod.agentsvault.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://valorant-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AgentsApi = retrofit.create(AgentsApi::class.java)
}