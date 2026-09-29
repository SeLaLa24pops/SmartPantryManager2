package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry_items";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity REAL, unit TEXT, expiry_date TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, instructions TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER, ingredient_name TEXT, required_quantity REAL, unit TEXT)");
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        onCreate(db);
    }

    public void addPantryItem(String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", qty);
        values.put("unit", unit);
        values.put("expiry_date", expiry);
        db.insert(TABLE_PANTRY, null, values);
        db.close();
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PANTRY, null);

        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private void seedRecipes(SQLiteDatabase db) {
        long id1 = addRecipe(db, "Mashed Potatoes", "Boil potatoes until soft. Mash with butter and milk. Season with salt.");
        addIngredientToRecipe(db, id1, "Potatoes", 4, "whole");
        addIngredientToRecipe(db, id1, "Butter", 2, "tbsp");
        addIngredientToRecipe(db, id1, "Milk", 0.5, "cup");
        addIngredientToRecipe(db, id1, "Salt", 1, "pinch");

        long id2 = addRecipe(db, "Onion Rings", "Slice onions into rings. Dip in batter. Deep fry until golden brown.");
        addIngredientToRecipe(db, id2, "Onions", 2, "whole");
        addIngredientToRecipe(db, id2, "Flour", 1, "cup");
        addIngredientToRecipe(db, id2, "Milk", 0.5, "cup");
        addIngredientToRecipe(db, id2, "Oil", 2, "cups");

        long id3 = addRecipe(db, "Omelette", "Whisk eggs with salt and pepper. Cook in a pan with butter.");
        addIngredientToRecipe(db, id3, "Eggs", 3, "whole");
        addIngredientToRecipe(db, id3, "Butter", 1, "tbsp");
        addIngredientToRecipe(db, id3, "Salt", 1, "pinch");
        addIngredientToRecipe(db, id3, "Pepper", 1, "pinch");

        long id4 = addRecipe(db, "Crispy Bacon", "Heat a pan. Add bacon strips. Cook until crispy on both sides.");
        addIngredientToRecipe(db, id4, "Bacon", 4, "strips");

        long id5 = addRecipe(db, "Tomato Pasta", "Boil pasta. Fry tomatoes with garlic. Mix together.");
        addIngredientToRecipe(db, id5, "Pasta", 200, "g");
        addIngredientToRecipe(db, id5, "Tomato", 2, "whole");
        addIngredientToRecipe(db, id5, "Garlic", 2, "cloves");

        long id6 = addRecipe(db, "Cheese Sandwich", "Butter bread. Add cheese slices. Toast in pan.");
        addIngredientToRecipe(db, id6, "Bread", 2, "slices");
        addIngredientToRecipe(db, id6, "Cheese", 2, "slices");
        addIngredientToRecipe(db, id6, "Butter", 1, "tbsp");

        long id7 = addRecipe(db, "Scrambled Eggs", "Whisk eggs. Cook in pan with butter, stirring constantly.");
        addIngredientToRecipe(db, id7, "Eggs", 2, "whole");
        addIngredientToRecipe(db, id7, "Butter", 1, "tbsp");
        addIngredientToRecipe(db, id7, "Milk", 2, "tbsp");

        long id8 = addRecipe(db, "Pancakes", "Mix flour, milk, and eggs. Cook on hot pan. Serve with syrup.");
        addIngredientToRecipe(db, id8, "Flour", 1, "cup");
        addIngredientToRecipe(db, id8, "Milk", 1, "cup");
        addIngredientToRecipe(db, id8, "Eggs", 1, "whole");

        long id9 = addRecipe(db, "Garlic Bread", "Mix butter and garlic. Spread on bread. Bake until crispy.");
        addIngredientToRecipe(db, id9, "Bread", 4, "slices");
        addIngredientToRecipe(db, id9, "Butter", 3, "tbsp");
        addIngredientToRecipe(db, id9, "Garlic", 2, "cloves");

        long id10 = addRecipe(db, "Fruit Salad", "Chop all fruits. Mix in a bowl. Add a squeeze of lemon.");
        addIngredientToRecipe(db, id10, "Apple", 1, "whole");
        addIngredientToRecipe(db, id10, "Banana", 1, "whole");
        addIngredientToRecipe(db, id10, "Lemon", 0.5, "whole");

        long id11 = addRecipe(db, "Chicken Stir-fry", "Cook chicken in pan. Add vegetables. Stir fry with soy sauce.");
        addIngredientToRecipe(db, id11, "Chicken", 200, "g");
        addIngredientToRecipe(db, id11, "Broccoli", 1, "cup");
        addIngredientToRecipe(db, id11, "Soy Sauce", 2, "tbsp");

        long id12 = addRecipe(db, "Rice Pudding", "Boil rice in milk. Add sugar. Simmer until thick.");
        addIngredientToRecipe(db, id12, "Rice", 1, "cup");
        addIngredientToRecipe(db, id12, "Milk", 2, "cups");
        addIngredientToRecipe(db, id12, "Sugar", 2, "tbsp");

        long id13 = addRecipe(db, "Beef Tacos", "Cook beef. Warm taco shells. Add beef and cheese.");
        addIngredientToRecipe(db, id13, "Beef", 200, "g");
        addIngredientToRecipe(db, id13, "Taco Shells", 3, "whole");
        addIngredientToRecipe(db, id13, "Cheese", 50, "g");

        long id14 = addRecipe(db, "Vegetable Soup", "Chop vegetables. Boil in broth until soft.");
        addIngredientToRecipe(db, id14, "Carrots", 2, "whole");
        addIngredientToRecipe(db, id14, "Celery", 2, "stalks");
        addIngredientToRecipe(db, id14, "Onions", 1, "whole");

        long id15 = addRecipe(db, "French Toast", "Dip bread in egg mixture. Fry until golden.");
        addIngredientToRecipe(db, id15, "Bread", 2, "slices");
        addIngredientToRecipe(db, id15, "Eggs", 1, "whole");
        addIngredientToRecipe(db, id15, "Milk", 2, "tbsp");
    }

    private long addRecipe(SQLiteDatabase db, String name, String instructions) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("instructions", instructions);
        return db.insert(TABLE_RECIPES, null, values);
    }

    private void addIngredientToRecipe(SQLiteDatabase db, long recipeId, String name, double qty, String unit) {
        ContentValues values = new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("ingredient_name", name);
        values.put("required_quantity", qty);
        values.put("unit", unit);
        db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String instructions = cursor.getString(2);
                Recipe recipe = new Recipe(id, name, instructions);
                recipe.setIngredients(getIngredientsForRecipe(db, id));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }

    private List<RecipeIngredient> getIngredientsForRecipe(SQLiteDatabase db, int recipeId) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT ingredient_name, required_quantity, unit FROM " + TABLE_RECIPE_INGREDIENTS + " WHERE recipe_id = ?", new String[]{String.valueOf(recipeId)});
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                double qty = cursor.getDouble(1);
                String unit = cursor.getString(2);
                ingredients.add(new RecipeIngredient(name, qty, unit));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }
}