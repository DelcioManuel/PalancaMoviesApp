package com.example.palancamovies.network

import com.example.palancamovies.telas.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Definir o BASE_URL correto para o seu servidor
    private const val BASE_URL = "http://192.168.26.35:3000/"  // Substitua pelo IP correto

    // Criação do Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Expondo a instância do serviço API
    val apiService: MovieApiService = retrofit.create(MovieApiService::class.java)
}
