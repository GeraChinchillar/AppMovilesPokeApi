package com.example.pokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.data.dao.RemoteKeyDao
import com.example.pokedex.data.entity.Pokemon
import com.example.pokedex.data.entity.RemoteKey
import java.security.AccessControlContext

@Database(entities = [Pokemon::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase(){

    abstract fun pokemonDao() : PokemonDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object{
        @Volatile
        private var INSTANCE : PokemonDatabase? = null

        fun getDatabase(context: Context) : PokemonDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "Pokemon_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}