package com.example.pokemonapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import org.w3c.dom.Text

class PokemonAdapter(private val dataSet: List<Pokemon2>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val pokemonContainer: ConstraintLayout = view.findViewById(R.id.pokemonContainer)
        val textView: TextView = view.findViewById(R.id.pokemonTextView)
        val idTextView: TextView = view.findViewById(R.id.pokemonIDTextView)

        init {
            pokemonContainer.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent: Intent = Intent(v!!.context, PokemonStatistics::class.java).putExtra("id", idTextView.text.toString())
            startActivity(v.context, intent, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_pokemon_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].getPokemonName()
        holder.idTextView.text = dataSet[position].getPokemonId().toString()
    }

    override fun getItemCount(): Int = dataSet.size
}