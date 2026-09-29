package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the data passed from the previous screen
        String name = getIntent().getStringExtra("RECIPE_NAME");
        String instructions = getIntent().getStringExtra("RECIPE_INSTRUCTIONS");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }

        TextView tvInstructions = findViewById(R.id.tvInstructions);
        tvInstructions.setText(instructions);
    }
}