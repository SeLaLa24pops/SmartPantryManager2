package com.example.smartpantrymanager;
//runs the strict matching algorithm
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvNoRecipes;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Suggested Recipes");
        }

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findMatchingRecipes();
    }

    private void findMatchingRecipes() {
        List<Recipe> allRecipes = dbHelper.getAllRecipes();
        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<Recipe> suggestedRecipes = RecipeMatcher.getStrictMatches(allRecipes, pantryItems);

        if (suggestedRecipes.isEmpty()) {
            tvNoRecipes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoRecipes.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            RecipeAdapter adapter = new RecipeAdapter(this, suggestedRecipes);
            recyclerView.setAdapter(adapter);
        }
    }
}