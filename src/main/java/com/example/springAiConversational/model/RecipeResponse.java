package com.example.springAiConversational.model;

import lombok.Data;
import java.util.List;

@Data
public class RecipeResponse {
    private String recipe_name;
    private String servings;
    private String estimated_cost;
    private List<Ingredient> ingredients;
    private List<String> steps;
}