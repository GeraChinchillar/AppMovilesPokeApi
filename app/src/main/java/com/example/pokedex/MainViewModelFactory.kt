package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.PokemonDatabase
import java.lang.Exception

class MainViewModelFactory(
    private val repository: PokemonRepository,
    private val database: PokemonDatabase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return  MainViewModel(repository, database) as T
        }
        throw Exception("View model class desconocido")
    }
}