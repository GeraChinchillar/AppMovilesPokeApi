package com.example.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.entity.Pokemon

class PokePageAdapter(diffCallback: DiffUtil.ItemCallback<Pokemon>):
    PagingDataAdapter<Pokemon, PokePageAdapter.PokemonViewHolder>(diffCallback) {

    class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val id = itemView.findViewById<TextView>(R.id.pokemon_id_text)
        val name = itemView.findViewById<TextView>(R.id.pokemon_name_text)

        fun bind(pokemon: Pokemon) {

            id.text = pokemon.id.toString()
            name.text = pokemon.name
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        if (pokemon != null) {
            holder.bind(pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)

        return PokemonViewHolder(view)
    }


}