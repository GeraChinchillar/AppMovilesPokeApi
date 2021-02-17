package com.example.pokedex.network.response

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokedex.data.PokemonDatabase
import com.example.pokedex.data.entity.Pokemon
import com.example.pokedex.data.entity.RemoteKey
import com.example.pokedex.network.PokeAPI
import timber.log.Timber
import java.lang.Exception
import java.security.PrivateKey

const val PAGE_KEY = "POKEMON"

@OptIn(ExperimentalPagingApi::class)
class PokemonMediator (
    private val database: PokemonDatabase,
    private val API: PokeAPI
        ): RemoteMediator<Int, Pokemon>(){

    val remoteKeyDao = database.remoteKeyDao()
    val pokemonDao = database.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try {

            val loadKey = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery((PAGE_KEY))
                    }

                    if(remoteKey.nextKey == null){
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey

                }
            }

            //Cargar la pagina

            val response = if (loadKey == null){
                API.retrofitService.getListPokemon(10)
            } else{
                API.retrofitService.getListPokemonByUrl(loadKey)
            }

            val pokemons = response.results.map {
                API.retrofitService.getPokemonByUrl(it.url)
            }

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    pokemonDao.deleteAll()
                    remoteKeyDao.deleteQuery(PAGE_KEY)
                }

                remoteKeyDao.insertOrReplace(RemoteKey(PAGE_KEY, response.next))
                pokemonDao.insertAll(pokemons)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.next.isNullOrBlank()
            )
        } catch (e: Exception){
            Timber.d(e)
            MediatorResult.Error(e)
        }
    }
}