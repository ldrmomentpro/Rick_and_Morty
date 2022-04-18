package com.eldiar.rickandmorty.repository

import androidx.paging.PagingData
import com.eldiar.rickandmorty.data.models.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getAllCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Int): Character
}