package com.example.pokedex.network.response

data class PokemonListResponse(
    var count : Int,
    var next: String?,
    var previous: String?,
    var results: List<PokemonMetaDataResponse>
)
