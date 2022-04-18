package com.eldiar.rickandmorty.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eldiar.rickandmorty.data.models.Character
import com.eldiar.rickandmorty.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CharactersViewModel(private val repo: CharactersRepository) : ViewModel() {

    private lateinit var _charactersList: Flow<PagingData<Character>>
    val characterList: Flow<PagingData<Character>>
        get() = _charactersList

    private suspend fun getAllCharacters(): Flow<PagingData<Character>> =
        repo.getAllCharacters()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val result =
                kotlin.runCatching { getAllCharacters().cachedIn(viewModelScope) }
            result.onSuccess { _charactersList = it }
        }
    }
}