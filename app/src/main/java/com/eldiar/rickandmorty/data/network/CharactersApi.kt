package com.eldiar.rickandmorty.data.network

import com.eldiar.rickandmorty.data.models.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): BaseResponse
}