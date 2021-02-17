package com.example.pokedex

import android.app.DownloadManager
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.data.entity.Pokemon
import com.example.pokedex.network.PokeAPI

class PokemonRepository(private val pokemonDao : PokemonDao) {
    private val API = PokeAPI.retrofitService

    suspend fun insert(pokemon: Pokemon) = pokemonDao.insert(pokemon)
    suspend fun getPokemon(query: String) = API.getPokemon(query)
    fun getSource() = pokemonDao.getSource()
}