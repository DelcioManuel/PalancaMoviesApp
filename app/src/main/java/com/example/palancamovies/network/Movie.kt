package com.example.palancamovies.network

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int? = null,

    val title: String,

    val synopsis: String,

    @SerializedName("releasedate")
    val releaseDate: String,

    @SerializedName("imageurl")
    val imageUrl: String
)

