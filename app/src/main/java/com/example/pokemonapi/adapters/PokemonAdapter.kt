package com.example.pokemonapi.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapi.Classes.Pokemon

class PokemonAdapter(private val dataSet: Array<Pokemon>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
              TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    // return the size of data set
    override fun getItemCount(): Int = dataSet.size
}