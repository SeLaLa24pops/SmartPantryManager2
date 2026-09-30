package com.example.smartpantrymanager;
//blueprint class like data model hold information
public class RecipeIngredient {
    private String name;
    private double requiredQuantity;
    private String unit;

    public RecipeIngredient(String name, double requiredQuantity, String unit) {
        this.name = name;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public String getName() { return name; }
    public double getRequiredQuantity() { return requiredQuantity; }
    public String getUnit() { return unit; }
}
