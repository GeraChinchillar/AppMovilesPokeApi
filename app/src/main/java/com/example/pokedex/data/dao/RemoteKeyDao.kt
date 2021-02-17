package com.example.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.data.entity.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remotes_keys WHERE label = :query")
    suspend fun remoteKeyByQuery(query: String) : RemoteKey

    @Query("DELETE FROM remotes_keys WHERE label = :query")
    suspend fun deleteQuery(query: String)
}