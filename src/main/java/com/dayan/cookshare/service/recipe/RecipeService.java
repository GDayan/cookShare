package com.dayan.cookshare.service.recipe;

import com.dayan.cookshare.dto.ImageDTO;
import com.dayan.cookshare.dto.RecipeDTO;
import com.dayan.cookshare.dto.ReviewDTO;
import com.dayan.cookshare.dto.UserDTO;
import com.dayan.cookshare.model.Image;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.model.Review;
import com.dayan.cookshare.model.User;
import com.dayan.cookshare.repository.ImageRepository;
import com.dayan.cookshare.repository.RecipeRepository;
import com.dayan.cookshare.repository.ReviewRepository;
import com.dayan.cookshare.repository.UserRepository;
import com.dayan.cookshare.request.CreateRecipeRequest;
import com.dayan.cookshare.request.UpdateRecipeRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if(request == null || request.getUser() == null){
            throw new IllegalArgumentException("Invalid request");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser -> {
                    existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(() -> {
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe = IRecipeService.createRecipe(request, user);
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
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);

    }

    @Override
    public Set<String> getAllRecipeCategories() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllRecipeCuisine() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCuisine)
                .collect(Collectors.toSet());
    }
    @Override
    public List<RecipeDTO> getConvertedRecipes(List<Recipe> recipes){
        return recipes.stream().map(this :: convertToDto).toList();
    }

    @Override
    public RecipeDTO convertToDto(Recipe recipe){
        RecipeDTO recipeDto = modelMapper.map(recipe, RecipeDTO.class);
        UserDTO userDto = modelMapper.map(recipe.getUser(), UserDTO.class);
        Optional<Image> image  = Optional.ofNullable(imageRepository.findByRecipeId(recipe.getId()));
        image.map(img -> modelMapper.map(img, ImageDTO.class)).ifPresent(recipeDto ::setImageDto);
        List<ReviewDTO> reviews = reviewRepository.findAllByRecipeId(recipe.getId())
                .stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class)).toList();

        recipeDto.setTotalRateCount(recipe.getTotalRateCount());
        recipeDto.setAverageRating(recipe.calculateAverageRatings());

        recipeDto.setUser(userDto);
        recipeDto.setReviews(reviews);
        return recipeDto;
    }
}
