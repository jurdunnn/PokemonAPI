package com.example.pokemonapi.Classes

import java.net.URL

object Pokemon {
    lateinit var name: String
    lateinit var url: URL

    fun Pokemon(mName: String, mUrl: URL) {
        name = mName
        url = mUrl
    }

    fun setPokemonName(mName: String){
        name = mName
    }

    fun getPokemonName(): String {
        return name
    }

    fun setPokemonURL(mUrl: URL) {
        url = mUrl
    }

    fun getPokemonURL(): URL {
        return url
    }

}