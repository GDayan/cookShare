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

    Recipe getRecipeById(Long id);

    Recipe updateRecipe(UpdateRecipeRequest request, Long recipeId);

    void deleteRecipe(Long recipeId);

    Set<String> getAllRecipeCategories();

    Set<String> getAllRecipeCuisine();


    static Recipe createRecipe(CreateRecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        Recipe createRequest = request.getRecipe();
        recipe.setTitle(createRequest.getTitle());
        recipe.setCuisine(createRequest.getCuisine());
        recipe.setCategory(createRequest.getCategory());
        recipe.setInstruction(createRequest.getInstruction());
        recipe.setDescription(createRequest.getDescription());
        recipe.setPrepTime(createRequest.getPrepTime());
        recipe.setCookTime(createRequest.getCookTime());
        recipe.setIngredients(createRequest.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, UpdateRecipeRequest request){
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setInstruction(request.getInstruction());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

    List<RecipeDTO> getConvertedRecipes(List<Recipe> recipes);

    RecipeDTO convertToDto(Recipe recipe);
}
