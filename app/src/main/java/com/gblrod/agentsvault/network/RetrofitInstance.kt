package com.gblrod.agentsvault.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://valorant-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: API = retrofit.create(API::class.java)
}