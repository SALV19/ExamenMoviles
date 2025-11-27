package com.example.nefrovida.data.local.model

import com.example.nefrovida.domain.model.Analysis

data class PokemonCache (
    val pokemonList: List<Analysis>,
    val lastUpdate: Long,
    val offset: Int,
    val totalCount: Int
)