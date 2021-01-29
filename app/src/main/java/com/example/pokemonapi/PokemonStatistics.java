package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility;
import me.sargunvohra.lib.pokekotlin.model.PokemonForm;
import me.sargunvohra.lib.pokekotlin.model.PokemonHeldItem;
import me.sargunvohra.lib.pokekotlin.model.PokemonMove;
import me.sargunvohra.lib.pokekotlin.model.PokemonStat;
import me.sargunvohra.lib.pokekotlin.model.PokemonType;
import me.sargunvohra.lib.pokekotlin.model.Version;
import me.sargunvohra.lib.pokekotlin.model.VersionGameIndex;

public class PokemonStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_statistics);

        //extras bundle containing int
        Bundle extras = getIntent().getExtras();
        //get id
        int id = Integer.parseInt(extras.getString("id"));
        //pokeapi
        PokeApi pokeApi = new PokeApiClient();
        //get pokemon with id
        Pokemon pokemon = pokeApi.getPokemon(id);

        //declare text views
        TextView pokeXP,
                pokeHeight,
                pokeWeight,
                pokeSpecies,
                pokeAbilities,
                pokeForms,
                pokeVersions,
                pokeHeldItems,
                pokeMoves,
                pokeStats,
                pokeTypes,
                pokeName,
                pokeID;

        //declare image views
        ImageView pokeSprite;

        //get image views
        pokeSprite = findViewById(R.id.pokeSprite);

        //get text views
        pokeID = findViewById(R.id.pokeID);
        pokeXP = findViewById(R.id.pokeXP);
        pokeHeight = findViewById(R.id.pokeHeight);
        pokeWeight = findViewById(R.id.pokeWeight);
        //pokeSpecies = findViewById(R.id.pokeSpecies);
        pokeAbilities = findViewById(R.id.pokeAbilities);
        //pokeForms = findViewById(R.id.pokeForms);
        pokeVersions = findViewById(R.id.pokeVersions);
        //pokeHeldItems = findViewById(R.id.pokeHeldItems);
        pokeMoves = findViewById(R.id.pokeMoves);
        pokeStats = findViewById(R.id.pokeStats);
        pokeTypes = findViewById(R.id.pokeTypes);
        pokeName = findViewById(R.id.pokeName);

        //populate image views
        // new background thread to run synchronous
        String urlStr = pokemon.getSprites().getFrontDefault(); //get string url

        //declare url and input stream
        URL myUrl = null;
        InputStream inputStream = null;

        //try and assign string url to url
        try {
            myUrl = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //tr and get url content using input stream
        try {
            assert myUrl != null;
            inputStream = (InputStream) myUrl.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create a drawable object from input stream
        Drawable drawable = Drawable.createFromStream(inputStream, null);
        //set drawable as image.
        pokeSprite.setImageDrawable(drawable);

        //populate text views
        //int types
        //try as database missing data
        try {
            pokeID.setText(String.valueOf(pokemon.getId()));
            pokeXP.setText(String.valueOf(pokemon.getBaseExperience()));
            pokeHeight.setText(String.valueOf(pokemon.getHeight()) + "dm");
            pokeWeight.setText(String.valueOf(pokemon.getWeight()) + "g");

            //string types
            pokeName.setText(pokemon.getName());

            //lists types
            pokeAbilities.setText(listToString(pokemon.getAbilities()));
            pokeVersions.setText(listToString(pokemon.getGameIndices()));
            pokeMoves.setText(listToString(pokemon.getMoves()));
            pokeStats.setText(listToString(pokemon.getStats()));
            pokeTypes.setText(listToString(pokemon.getTypes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String listToString(List<?> list) {
        //declare string builder
        StringBuilder string = new StringBuilder();

        //get class of object
        String cls = list.get(0).getClass().getName();
        Log.i("List classes:", cls);
        Log.i("List data", list.toString());
        //loop through lists
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                string.append(", ");
            }
            switch (cls) {
                case "me.sargunvohra.lib.pokekotlin.model.PokemonAbility":
                    PokemonAbility pokemonAbility = (PokemonAbility) list.get(i);
                    string.append(pokemonAbility.getAbility().getName());
                    break;
                case "me.sargunvohra.lib.pokekotlin.model.VersionGameIndex":
                    VersionGameIndex version = (VersionGameIndex) list.get(i);
                    string.append(version.getVersion().getName());
                    break;
                case "me.sargunvohra.lib.pokekotlin.model.PokemonMove":
                    PokemonMove pokemonMove = (PokemonMove) list.get(i);
                    string.append(pokemonMove.getMove().getName());
                    break;
                case "me.sargunvohra.lib.pokekotlin.model.PokemonStat":
                    PokemonStat pokemonStat = (PokemonStat) list.get(i);
                    string.append(pokemonStat.getStat().getName()).append(": ").append(pokemonStat.getBaseStat());
                    break;
                case "me.sargunvohra.lib.pokekotlin.model.PokemonType":
                    PokemonType pokemonType = (PokemonType) list.get(i);
                    string.append(pokemonType.getType().getName());
                    break;
            }
        }
        return string.toString();
    }
}