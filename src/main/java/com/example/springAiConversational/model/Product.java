package com.example.springAiConversational.model;

import lombok.Data;

@Data
public class Product {
    public String name;
    public double price;
    public String unit;
    private double quantity;
    // Optional (recommended for future cart integration)
    public String id;
}