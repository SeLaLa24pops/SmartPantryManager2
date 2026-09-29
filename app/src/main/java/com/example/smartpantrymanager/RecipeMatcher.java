package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {

    public static List<Recipe> getStrictMatches(List<Recipe> allRecipes, List<PantryItem> pantryItems) {
        List<Recipe> suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean canMakeRecipe = true;

            for (RecipeIngredient req : recipe.getIngredients()) {
                boolean hasIngredient = false;

                for (PantryItem pantryItem : pantryItems) {
                    // 1. Handle "real-world messiness": Normalize names (lowercase, remove plural 's')
                    String pantryName = normalizeName(pantryItem.getName());
                    String reqName = normalizeName(req.getName());

                    if (pantryName.equals(reqName)) {
                        // 2. Check if quantity is sufficient
                        if (pantryItem.getQuantity() >= req.getRequiredQuantity()) {
                            hasIngredient = true;
                            break;
                        }
                    }
                }

                if (!hasIngredient) {
                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                suggestedRecipes.add(recipe);
            }
        }
        return suggestedRecipes;
    }

    private static String normalizeName(String name) {
        String lower = name.toLowerCase().trim();
        if (lower.endsWith("s") && lower.length() > 3) {
            return lower.substring(0, lower.length() - 1);
        }
        return lower;
    }
}