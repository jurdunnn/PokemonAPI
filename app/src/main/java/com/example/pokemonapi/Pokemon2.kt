package com.example.pokemonapi

public class Pokemon2 {
    lateinit var name: String
    lateinit var category: String
    var id: Int = 0

    public fun Pokemon2(mName: String, mCategory: String, mId: Int) {
        name = mName
        category = mCategory
        id = mId
    }

    fun setPokemonName(mName: String) {
        name = mName
    }

    fun getPokemonName(): String {
        return name
    }

    fun setPokemonCategory(mCategory: String) {
        category = mCategory
    }

    fun getPokemonCategory(): String {
        return category
    }

    fun setPokemonId(mId: Int) {
        id = mId
    }

    fun getPokemonId(): Int {
        return id
    }

}