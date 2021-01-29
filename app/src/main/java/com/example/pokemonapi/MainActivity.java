package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;

public class MainActivity extends AppCompatActivity {

    private String url = "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20";
    public List<Pokemon2> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //run background network thread
        pokemonList = new ArrayList<>();
        getPokemonList.run();

        //adapter
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemonList);

        //recycler view
        //recycler view and adapter
        RecyclerView recyclerView = findViewById(R.id.pokemonView);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //buttons
        //buttons
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get offset number
                int offset = getOffset(url);
                //math for new offset
                int newOffset = offset + 20;
                //update the URL with new off set
                updateURL(offset, newOffset);
            }
        });
        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get offset number
                int offset = (int) getOffset(url);
                //math for new offset
                int newOffset = offset - 20;
                //ensure the user does not go into the minuses
                if (newOffset < 0) {
                    Toast.makeText(MainActivity.this, "You are already on the 1st page.", Toast.LENGTH_SHORT).show();
                } else {
                    //update the URL with new off set
                    updateURL(offset, newOffset);
                }
            }
        });
    }

    Thread getPokemonList = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //pokemon wrapper
                PokeApi pokeApi = new PokeApiClient();
                List<NamedApiResource> list = pokeApi.getPokemonList(20,20).getResults();
                for(int i = 0; i <= list.size(); i++) {
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

    private void getAPI() {

    }

    private void updateURL(int offset, int newOffset) {
        //to String
        String target = "offset=" + offset;
        String replacement = "offset=" + newOffset;
        //replace offset
        url = url.replace(target, replacement);
        Log.i("URL", url);
    }

    public static int getOffset(final CharSequence input) {
        //case input to String Builder
        final StringBuilder sb = new StringBuilder(input);
        //find offset keyword for start of loop
        int start = sb.indexOf("offset");
        //find limit word for stop loop
        int limit = sb.indexOf("limit");
        //new string for StringBuilder for digits
        StringBuilder digits = new StringBuilder();

        //find digits
        for (int i = start; i < limit; i++) {
            final char c = input.charAt(i);
            if (c > 47 && c < 58) {
                digits.append(c);
            }
        }

        //cast string to integer for math and return
        return Integer.parseInt(digits.toString());
    }

}