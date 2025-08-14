package com.dayan.cookshare.controller;

import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.request.CreateRecipeRequest;
import com.dayan.cookshare.request.UpdateRecipeRequest;
import com.dayan.cookshare.service.recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final IRecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(CreateRecipeRequest request){
        return ResponseEntity.ok(recipeService.createRecipe(request));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipe(){
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }

    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipeId){
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<Recipe> updateRecipe(UpdateRecipeRequest request, @PathVariable Long recipeId){
        return ResponseEntity.ok(recipeService.updateRecipe(request, recipeId));
    }

    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId){
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getAllCategories(){
        return ResponseEntity.ok(recipeService.getAllRecipeByCategories());
    }

    @GetMapping("/cuisines")
    public ResponseEntity<Set<String>> getAllCuisines(){
        return ResponseEntity.ok(recipeService.getAllRecipeByCuisine());
    }
}
