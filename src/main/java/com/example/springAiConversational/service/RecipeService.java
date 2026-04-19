package com.example.springAiConversational.service;

import com.example.springAiConversational.constants.PromptConstants;
//import com.example.springAiConversational.data.ProductData;
import com.example.springAiConversational.dataInitializer.ProductData;
import com.example.springAiConversational.model.Product;
import com.example.springAiConversational.model.RecipeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    private ChatClient chatClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public Object getRecipe(RecipeRequest request) {
        try {
            List<Product> products = request.available_products;

            int budget = extractBudget(request.user_request);
            int people = extractPeople(request.user_request);

            String prompt = buildPrompt(request.user_request, products, budget, people);

            String response = chatClient
                    .prompt()
                    .system(PromptConstants.SYSTEM_PROMPT)
                    .user(prompt)
                    .call()
                    .content();
            response = cleanJson(response);
            return mapper.readTree(response);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Invalid AI response. Try again.");
        }
    }

//    private String buildPrompt(String userRequest, List<Product> products, int budget, int people) {
//
//        StringBuilder productList = new StringBuilder();
//
//        int limit = Math.min(products.size(), 25);
//
//        for (int i = 0; i < limit; i++) {
//            Product p = products.get(i);
//
//            productList.append("- ")
//                    .append(p.name)
//                    .append(" ₹")
//                    .append(p.price)
//                    .append(" per ")
//                    .append(p.unit)
//                    .append("\n");
//        }
//
//        return """
//User request:
//%s
//
//Derived constraints:
//- Budget: %d INR
//- People: %d
//
//Instructions:
//- Use budget effectively (DO NOT underutilize budget)
//- Try to reach close to budget (70%%–100%%)
//- Adjust quantities for %d people
//
//Available products (STRICT - use only these):
//%s
//""".formatted(userRequest, budget, people, people, productList.toString());
//    }
private String buildPrompt(String userRequest, List<Product> products, int budget, int people) {

    StringBuilder productList = new StringBuilder();

    int limit = Math.min(products.size(), 25);

    for (int i = 0; i < limit; i++) {
        Product p = products.get(i);

        productList.append("- ")
                .append(p.name)
                .append(" ₹")
                .append(p.price)
                .append(" per ")
                .append(p.unit)
                .append("\n");
    }

    String budgetInstruction = (budget > 0)
            ? "- Budget: " + budget + " INR\n- Stay within budget"
            : "- No strict budget. Focus on sufficient quantity for all people";

    return """
User request:
%s

Derived constraints:
%s
- People: %d

Instructions:
- Ensure enough food for %d people
- Do NOT reduce quantity due to budget if not provided
- Use realistic ingredient quantities

Available products (STRICT - use only these):
%s
""".formatted(userRequest, budgetInstruction, people, people, productList.toString());
}

    // 🔥 Extract budget like "150", "150rs"
    private int extractBudget(String text) {
        text = text.toLowerCase();

        if (text.contains("rs") || text.contains("₹") || text.contains("budget")) {
            String digits = text.replaceAll("[^0-9]", "");
            if (!digits.isEmpty()) {
                return Integer.parseInt(digits);
            }
        }

        return -1; // ❗ means "no budget constraint"
    }

    // 🔥 Extract people count
    private int extractPeople(String text) {
        text = text.toLowerCase();

        // Match patterns like "10 people"
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("(\\d+)\\s*people")
                .matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 4; // default
    }
    private String cleanJson(String response) {
        if (response == null) return "";

        // remove ```json and ```
        response = response.replaceAll("```json", "");
        response = response.replaceAll("```", "");

        return response.trim();
    }
}