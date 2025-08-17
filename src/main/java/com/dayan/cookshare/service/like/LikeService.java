package com.dayan.cookshare.service.like;

import com.dayan.cookshare.model.Like;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.repository.LikeRepository;
import com.dayan.cookshare.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService{
    private final RecipeRepository recipeRepository;
    private final LikeRepository likeRepository;
    @Override
    public int likeRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            if(!likeRepository.existsByRecipeId(recipe.getId())){
                Like like = new Like(recipe);
                likeRepository.save(like);
            }
            recipe.setLikeCount(recipe.getLikeCount() + 1);
            return recipeRepository.save(recipe).getLikeCount();
        }).orElseThrow(() -> new EntityNotFoundException("Recipe not found!"));
    }

    @Override
    public int unLikeRecipe(Long recipeId) {
        return likeRepository.findFirstByRecipeId(recipeId).map(like -> {
            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
            if(recipe.getLikeCount() > 0){
                recipe.setLikeCount(recipe.getLikeCount() - 1);
                recipeRepository.save(recipe);
            } else {
                throw new IllegalArgumentException("Like is already zero");
            }
            return recipe.getLikeCount();
        }).orElseThrow(() -> new EntityNotFoundException("No likes found for this recipe"));
    }

    @Override
    public Long getLikeCount(Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipe -> likeRepository.countByRecipeId(recipe.getId())).orElse(0L);
    }
}
