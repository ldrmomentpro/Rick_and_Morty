package com.eldiar.rickandmorty.repository

import com.eldiar.rickandmorty.data.models.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getAllCharacters(): Flow<List<Character>>
}