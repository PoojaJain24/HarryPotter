package com.example.harrypotterapp.model.api

import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://hp-api.onrender.com/"
    }

    @GET("api/characters")
    suspend fun getAllCharacters(): Response<List<HarryPotterCharacter>>
}