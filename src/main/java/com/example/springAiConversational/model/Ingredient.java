package com.example.springAiConversational.model;


import lombok.Data;

@Data

public class Ingredient {
    private String name;
    private String quantity;
    private String unit;
    private boolean is_mandatory;
}