package com.example.pokedex

import android.app.Application
import com.example.pokedex.data.PokemonDatabase
import timber.log.Timber

class MyApplication : Application() {
    val database by lazy { PokemonDatabase.getDatabase(this) }
    val pokemonRepository by lazy { PokemonRepository(database.pokemonDao()) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}