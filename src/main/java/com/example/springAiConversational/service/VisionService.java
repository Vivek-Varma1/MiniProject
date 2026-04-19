package com.example.springAiConversational.service;

import com.example.springAiConversational.constants.PromptConstants;
import com.example.springAiConversational.dataInitializer.ProductData;
import com.example.springAiConversational.model.Product;
import com.example.springAiConversational.model.RecipeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class VisionService {

    @Autowired
    private ChatClient chatClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public Object extractIngredientsFromImage(MultipartFile file, RecipeRequest request) {
        try {
            List<Product> products = request.available_products;

            String prompt = buildPrompt(products);

            MimeType mimeType = MimeType.valueOf(file.getContentType());

            ByteArrayResource resource =
                    new ByteArrayResource(file.getBytes());

            String response = chatClient
                    .prompt()
                    .system(PromptConstants.VISION_SYSTEM_PROMPT) // 👈 use new prompt
                    .user(user -> user
                            .text(prompt)
                            .media(mimeType, resource)
                    )
                    .call()
                    .content();

            return mapper.readTree(response);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to process image.");
        }
    }

    private String buildPrompt(List<Product> products) {

        StringBuilder productList = new StringBuilder();

        int limit = Math.min(products.size(), 50); // more context for matching

        for (int i = 0; i < limit; i++) {
            Product p = products.get(i);

            productList.append("- ")
                    .append(p.name)
                    .append(" (")
                    .append(p.unit)
                    .append(")\n");
        }

        return """
Analyze the given image.

Task:
- Extract grocery/product names from the image.
- Match them with the available product list.

Available products (STRICT - use only these):
%s
""".formatted(productList.toString());
    }
}