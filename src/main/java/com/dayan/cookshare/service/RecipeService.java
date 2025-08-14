package com.dayan.cookshare.service;

import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.model.User;
import com.dayan.cookshare.repository.RecipeRepository;
import com.dayan.cookshare.repository.UserRepository;
import com.dayan.cookshare.request.CreateRecipeRequest;
import com.dayan.cookshare.request.UpdateRecipeRequest;
import com.dayan.cookshare.service.recipe.IRecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if(request == null || request.getUser() == null){
            throw new IllegalArgumentException("не верный запрос");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser -> {existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(() -> {
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe= IRecipeService.createRecipe(request, user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(UpdateRecipeRequest request, Long recipeId) {
        Recipe recipe = getRecipeById(recipeId);
        Recipe theRecipe = IRecipeService.updateRecipe(recipe, request);
        return recipeRepository.save(theRecipe);
    }

    @Override
    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Рецепт не найден"));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public Set<String> getAllRecipeByCategories() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllRecipeByCuisine() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCuisine)
                .collect(Collectors.toSet());
    }
}
