package com.example.springAiConversational.controller;


import com.example.springAiConversational.dataInitializer.ProductData;
import com.example.springAiConversational.model.RecipeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.springAiConversational.service.RecipeService;
import com.example.springAiConversational.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/recipe")
@CrossOrigin("*")
public class RecipeController {

      @Autowired
    private RecipeService recipeService;

    @Autowired
    private VisionService visionService;

    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

    @PostMapping
    public ResponseEntity<?> generateRecipe(@RequestBody RecipeRequest request) {
        Object response = recipeService.getRecipe(request);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/recipieWithImage")
//    public  String chatImage(@RequestPart("image") MultipartFile file){
//        return visionService.extractIngredientsFromImage(file);
//    }
@PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> extractIngredients(
        @RequestPart("file") MultipartFile file,
        @RequestPart("request") String requestJson) {

    try {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("File is empty. Please upload a valid image.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest()
                    .body("Only image files are allowed.");
        }

        RecipeRequest request = new ObjectMapper()
                .readValue(requestJson, RecipeRequest.class);



        Object response = visionService.extractIngredientsFromImage(file, request);

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        log.error("Error processing image", e);
        return ResponseEntity.internalServerError()
                .body("Failed to process image.");
    }
}
}