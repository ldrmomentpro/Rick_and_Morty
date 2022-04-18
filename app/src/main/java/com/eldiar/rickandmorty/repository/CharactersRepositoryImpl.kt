package com.eldiar.rickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eldiar.rickandmorty.data.models.Character
import com.eldiar.rickandmorty.data.network.CharactersApi
import com.eldiar.rickandmorty.data.pagingsource.CharactersPagingSource
import com.eldiar.rickandmorty.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class CharactersRepositoryImpl(private val api: CharactersApi) : CharactersRepository {

    override suspend fun getAllCharacters(): Flow<PagingData<Character>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            CharactersPagingSource(api)
        }.flow
    }
}