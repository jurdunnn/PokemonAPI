package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url = "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20";

    //buttons
    private Button nextButton;
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get offset number
                int offset = getOffset(url);
                //math for new offset
                int newOffset = offset + 20;
                //to String
                String target = "offset=" + offset;
                String replacement = "offset=" + newOffset;
                //replace offset
                url = url.replace(target, replacement);
                Log.i("URL", url);
            }
        });
        previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get offset number
                int offset = (int) getOffset(url);
                //math for new offset
                int newOffset = offset - 20;
                //to String
                String target = "offset=" + offset;
                String replacement = "offset=" + newOffset;
                //ensure the user does not go into the minuses
                if(newOffset < 0) {
                    Toast.makeText(MainActivity.this, "You are already on the 1st page.", Toast.LENGTH_SHORT).show();
                } else {
                    //replace offset
                    url = url.replace(target, replacement);
                    Log.i("URL", url);
                }
            }
        });

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