package com.eldiar.rickandmorty.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldiar.rickandmorty.data.models.Character
import com.eldiar.rickandmorty.repository.CharactersRepository
import com.eldiar.rickandmorty.utils.ApiStatus
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val repo: CharactersRepository) : ViewModel() {

    private val _characterDetail = MutableLiveData<Character>()
    val characterDetail: LiveData<Character> get() = _characterDetail

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val result = kotlin.runCatching { repo.getCharacterById(id) }
            result.onSuccess {
                _characterDetail.value = it
                _status.value = ApiStatus.DONE
            }
            result.onFailure { _status.value = ApiStatus.ERROR }
        }
    }
}