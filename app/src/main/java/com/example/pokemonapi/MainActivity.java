package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;

public class MainActivity extends AppCompatActivity {
    //pokemon list
    public List<Pokemon2> pokemonList;
    //adapter for showing each pokemon
    private PokemonAdapter pokemonAdapter;
    //page offset
    private int offset;
    //recycler view
    RecyclerView recyclerView;
    //search bar
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //run background network thread
        pokemonList = new ArrayList<>();
        getPokemonList.run();

        //default offset
        offset = 0;

        //adapter
        pokemonAdapter = new PokemonAdapter(pokemonList);

        //recycler view
        recyclerView = findViewById(R.id.pokemonView);
        recyclerView.hasFixedSize();
        setRecyclerView(); //set recycler view.

        //search bar
        searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pokemonList.clear();
                searchPokemonList.run();
                setRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //next and previous page buttons
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            offset += 20; //add 20 to the offset
            pokemonList.clear(); //clear existing list
            getPokemonList.run(); //get list
            setRecyclerView(); //add list to view
        });
        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(v -> {
            offset -= 20; //minus 20 to the offset
            pokemonList.clear(); //clear existing list
            getPokemonList.run(); //get list
            setRecyclerView(); //add list to view
        });
    }

    Thread searchPokemonList = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //pokemon wrapper
                PokeApi pokeApi = new PokeApiClient();
                List<NamedApiResource> list = pokeApi.getPokemonList(0, 1000).getResults();
                for (int i = 0; i <= list.size(); i++) {
                    Pokemon2 pokemon = new Pokemon2();

                    //get pokemon from API list and create pokemon object, pass that into List<Pokemon>
                    if(list.get(i).getName().contains(searchBar.getText())) {
                        pokemon.setName(list.get(i).getName());
                        pokemon.setCategory(list.get(i).getCategory());
                        pokemon.setId(list.get(i).getId());
                        pokemonList.add(pokemon);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    Thread getPokemonList = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //pokemon wrapper
                PokeApi pokeApi = new PokeApiClient();
                List<NamedApiResource> list = pokeApi.getPokemonList(offset, 20).getResults();
                for (int i = 0; i <= list.size(); i++) {
                    Pokemon2 pokemon = new Pokemon2();

                    //get pokemon from API list and create pokemon object, pass that into List<Pokemon>
                    pokemon.setName(list.get(i).getName());
                    pokemon.setCategory(list.get(i).getCategory());
                    pokemon.setId(list.get(i).getId());

                    pokemonList.add(pokemon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    private void setRecyclerView() {
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Permit threads for network activity if version is newer than version 9
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}