package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.entity.Pokemon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        val myApplication = application as MyApplication
        val pokemonRepository = myApplication.pokemonRepository
        val database = myApplication.database
        MainViewModelFactory(pokemonRepository, database)
    }

    private val viewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    object PokemonComparator: DiffUtil.ItemCallback<Pokemon>(){
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onFindPokemon();

        viewModel.pokemonInfo.observe(this){
            val nameView = findViewById<TextView>(R.id.pokemon_name_text)
            nameView.text = it.name
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_pokemon_list)
        val adapter = PokePageAdapter(PokemonComparator)

        recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.pager.flow.collectLatest { pokemonPageSource ->
                adapter.submitData(pokemonPageSource)
            }
        }

    }

    fun onFindPokemon(){
        val button = findViewById<Button>(R.id.action_search_pokemon)
        button.setOnClickListener{
            val queryView = findViewById<EditText>(R.id.pokemon_query_text)
            val query = queryView.text.toString()
            viewModel.getPokemon(query)
        }
    }
}