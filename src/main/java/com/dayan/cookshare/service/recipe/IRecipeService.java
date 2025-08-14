package com.dayan.cookshare.service.recipe;

import com.dayan.cookshare.dto.RecipeDTO;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.model.User;
import com.dayan.cookshare.request.CreateRecipeRequest;
import com.dayan.cookshare.request.UpdateRecipeRequest;

import java.util.List;
import java.util.Set;

public interface IRecipeService {
    Recipe createRecipe(CreateRecipeRequest request);
    List<Recipe> getAllRecipe();
    Recipe getRecipeById(Long recipeId);
    Recipe updateRecipe(UpdateRecipeRequest request, Long recipeId);

    void deleteRecipe(Long recipeId);
    Set<String> getAllRecipeByCategories();
    Set<String> getAllRecipeByCuisine();

    static Recipe createRecipe(CreateRecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        Recipe createRequest = request.getRecipe();
        recipe.setTitle(createRequest.getTitle());
        recipe.setCategory(createRequest.getCategory());
        recipe.setCuisine(createRequest.getCuisine());
        recipe.setInstruction(createRequest.getInstruction());
        recipe.setDescription(createRequest.getDescription());
        recipe.setCookTime(createRequest.getCookTime());
        recipe.setPrepTime(createRequest.getPrepTime());
        recipe.setIngredients(createRequest.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, UpdateRecipeRequest request) {
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setInstruction(request.getInstruction());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

    List<RecipeDTO> getConvertedRecipes(List<Recipe> recipes);

    RecipeDTO convertToDto(Recipe recipe);
}
