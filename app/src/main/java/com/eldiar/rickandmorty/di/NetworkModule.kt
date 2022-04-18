package com.eldiar.rickandmorty.di

import com.eldiar.rickandmorty.data.network.CharactersApi
import com.eldiar.rickandmorty.utils.Constants.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> { provideRetrofit() }
    single<CharactersApi> { provideServiceApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideServiceApi(retrofit: Retrofit): CharactersApi =
    retrofit.create(CharactersApi::class.java)
