package com.example.binapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {
    @GET("{id}")
    suspend fun getBin(@Path("id") binId: Int): Response<Bin>
}

object RetrofitInstance {
    private const val BASE_URL = "https://lookup.binlist.net/"

    val api: BinApi by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinApi::class.java)
    }
}