package com.eldiar.rickandmorty.di

import com.eldiar.rickandmorty.repository.CharactersRepository
import com.eldiar.rickandmorty.repository.CharactersRepositoryImpl
import com.eldiar.rickandmorty.ui.detail.CharacterDetailViewModel
import com.eldiar.rickandmorty.ui.main.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CharactersRepository> { CharactersRepositoryImpl(get()) }
    viewModel<CharactersViewModel> { CharactersViewModel(get()) }
    viewModel<CharacterDetailViewModel> { CharacterDetailViewModel(get()) }
}