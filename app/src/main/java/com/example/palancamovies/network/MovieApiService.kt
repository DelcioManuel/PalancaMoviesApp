package com.example.palancamovies.telas

import com.example.palancamovies.network.Movie
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovieApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: Int): Movie

    @POST("movies")
    suspend fun createMovie(@Body movie: Movie): ResponseBody

    @PUT("movies/{id}")
    suspend fun updateMovie(@Path("id") id: Int, @Body movie: Movie): ResponseBody

    @DELETE("movies/{id}")
    suspend fun deleteMovie(@Path("id") id: Int): ResponseBody
}