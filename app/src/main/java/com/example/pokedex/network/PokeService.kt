package com.example.pokedex.network

import android.app.DownloadManager
import com.example.pokedex.data.entity.Pokemon
import com.example.pokedex.network.response.PokemonListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

private const val POKE_URL = "https://pokeapi.co/api/v2/"

private val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(POKE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface PokeService {
    @GET("pokemon/{query}")
    suspend fun getPokemon(@Path("query")query: String) : Pokemon

    @GET
    suspend fun getListPokemon (@Query("limit")limit: Int): PokemonListResponse

    @GET
    suspend fun getListPokemonByUrl(@Url url: String): PokemonListResponse

    @GET
    suspend fun getPokemonByUrl(@Url url: String): Pokemon
}

object PokeAPI {
    val retrofitService : PokeService by lazy{
        retrofit.create(PokeService::class.java)
    }
}