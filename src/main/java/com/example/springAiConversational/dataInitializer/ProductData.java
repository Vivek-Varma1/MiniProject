package com.example.springAiConversational.dataInitializer;

import com.example.springAiConversational.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductData {

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        //  Staples
        products.add(create("Rice", 60, "kg"));
        products.add(create("Wheat Flour", 45, "kg"));
        products.add(create("Rava", 50, "kg"));
        products.add(create("Poha", 55, "kg"));
        products.add(create("Maida", 48, "kg"));

        //  Pulses
        products.add(create("Toor Dal", 120, "kg"));
        products.add(create("Moong Dal", 110, "kg"));
        products.add(create("Chana Dal", 90, "kg"));
        products.add(create("Urad Dal", 130, "kg"));
        products.add(create("Rajma", 140, "kg"));

        //  Oils
        products.add(create("Sunflower Oil", 130, "liter"));
        products.add(create("Groundnut Oil", 160, "liter"));
        products.add(create("Mustard Oil", 150, "liter"));
        products.add(create("Olive Oil", 500, "liter"));
        products.add(create("Coconut Oil", 180, "liter"));

        //  Dairy
        products.add(create("Milk", 50, "liter"));
        products.add(create("Curd", 35, "500g"));
        products.add(create("Butter", 55, "100g"));
        products.add(create("Cheese", 90, "200g"));
        products.add(create("Paneer", 85, "200g"));

        //  Eggs & Meat
        products.add(create("Eggs", 6, "piece"));
        products.add(create("Chicken", 220, "kg"));
        products.add(create("Mutton", 900, "kg"));
        products.add(create("Fish", 180, "kg"));

        //  Essentials
        products.add(create("Salt", 20, "kg"));
        products.add(create("Sugar", 40, "kg"));
        products.add(create("Jaggery", 60, "kg"));
        products.add(create("Tea Powder", 250, "kg"));
        products.add(create("Coffee Powder", 400, "kg"));

        //  Spices
        products.add(create("Turmeric Powder", 200, "kg"));
        products.add(create("Chilli Powder", 220, "kg"));
        products.add(create("Coriander Powder", 180, "kg"));
        products.add(create("Garam Masala", 300, "kg"));
        products.add(create("Cumin Seeds", 250, "kg"));

        //  Vegetables
        products.add(create("Onion", 30, "kg"));
        products.add(create("Potato", 25, "kg"));
        products.add(create("Tomato", 28, "kg"));
        products.add(create("Carrot", 40, "kg"));
        products.add(create("Beans", 50, "kg"));
        products.add(create("Cabbage", 25, "kg"));
        products.add(create("Cauliflower", 30, "kg"));
        products.add(create("Brinjal", 35, "kg"));
        products.add(create("Capsicum", 70, "kg"));
        products.add(create("Spinach", 20, "bundle"));

        //  Herbs
        products.add(create("Coriander Leaves", 10, "bundle"));
        products.add(create("Mint Leaves", 10, "bundle"));
        products.add(create("Curry Leaves", 5, "bundle"));

        //  Fruits
        products.add(create("Banana", 5, "piece"));
        products.add(create("Apple", 120, "kg"));
        products.add(create("Orange", 80, "kg"));
        products.add(create("Mango", 150, "kg"));
        products.add(create("Grapes", 90, "kg"));
        products.add(create("Pomegranate", 140, "kg"));
        products.add(create("Papaya", 50, "kg"));
        products.add(create("Watermelon", 30, "kg"));

        //  More veggies
        products.add(create("Garlic", 120, "kg"));
        products.add(create("Ginger", 100, "kg"));
        products.add(create("Green Chilli", 60, "kg"));
        products.add(create("Beetroot", 45, "kg"));
        products.add(create("Radish", 35, "kg"));
        products.add(create("Sweet Corn", 20, "piece"));
        products.add(create("Peas", 80, "kg"));
        products.add(create("Drumstick", 60, "kg"));

        //  Bakery
        products.add(create("Bread", 40, "packet"));
        products.add(create("Buns", 30, "packet"));
        products.add(create("Cake", 300, "kg"));
        products.add(create("Biscuits", 20, "packet"));

        //  Others
        products.add(create("Honey", 250, "kg"));
        products.add(create("Jam", 150, "kg"));
        products.add(create("Ketchup", 120, "kg"));
        products.add(create("Vinegar", 90, "liter"));
        products.add(create("Soy Sauce", 110, "liter"));

        //  Frozen / misc
        products.add(create("Ice Cream", 200, "liter"));
        products.add(create("Frozen Peas", 120, "kg"));
        products.add(create("Frozen Corn", 130, "kg"));

        //  Dry fruits
        products.add(create("Almonds", 700, "kg"));
        products.add(create("Cashews", 800, "kg"));
        products.add(create("Raisins", 300, "kg"));
        products.add(create("Dates", 250, "kg"));
        products.add(create("Pistachios", 900, "kg"));

        //  Beverages
        products.add(create("Soft Drink", 40, "liter"));
        products.add(create("Fruit Juice", 80, "liter"));
        products.add(create("Coconut Water", 40, "piece"));

        return products;
    }

    private static Product create(String name, double price, String unit) {
        Product p = new Product();
        p.id = name.replaceAll(" ", "_").toLowerCase(); // clean id
        p.name = name;
        p.price = price;
        p.unit = unit;
        return p;
    }
}