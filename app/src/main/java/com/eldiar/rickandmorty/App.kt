package com.eldiar.rickandmorty

import android.app.Application
import com.eldiar.rickandmorty.di.appModule
import com.eldiar.rickandmorty.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule)
        }
    }
}