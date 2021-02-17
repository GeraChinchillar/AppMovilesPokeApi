package com.example.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remotes_keys")
data class RemoteKey(
    @PrimaryKey
    val label: String,
    val nextKey: String?
)
