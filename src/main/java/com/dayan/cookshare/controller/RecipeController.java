package com.dayan.cookshare.controller;

import com.dayan.cookshare.dto.RecipeDTO;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.request.CreateRecipeRequest;
import com.dayan.cookshare.request.UpdateRecipeRequest;
import com.dayan.cookshare.service.recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final IRecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody CreateRecipeRequest request){
        Recipe recipe = recipeService.createRecipe(request);
        return ResponseEntity.ok(recipeService.convertToDto(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(){
        List<Recipe> recipes = recipeService.getAllRecipe();
        List<RecipeDTO> recipeDto = recipeService.getConvertedRecipes(recipes);
        return ResponseEntity.ok(recipeDto);
    }
    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long recipeId){
        RecipeDTO recipe = recipeService.convertToDto(recipeService.getRecipeById(recipeId));
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long recipeId, @RequestBody UpdateRecipeRequest request) {
        Recipe updatedRecipe = recipeService.updateRecipe(request,recipeId);
        RecipeDTO recipeDto = recipeService.convertToDto(updatedRecipe);
        return ResponseEntity.ok(recipeDto);
    }



    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getAllCategories(){
        return ResponseEntity.ok(recipeService.getAllRecipeCategories());
    }

    @GetMapping("/cuisines")
    public ResponseEntity<Set<String>> getAllCuisines(){
        return ResponseEntity.ok(recipeService.getAllRecipeCuisine());
    }


}
