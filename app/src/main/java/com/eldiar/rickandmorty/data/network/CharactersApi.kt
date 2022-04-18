package com.eldiar.rickandmorty.data.network

import com.eldiar.rickandmorty.data.models.BaseResponse
import com.eldiar.rickandmorty.data.models.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): BaseResponse

    @GET("character/{id}")
    suspend fun getCharacterDetailById(
        @Path("id") id: Int
    ): Character
}