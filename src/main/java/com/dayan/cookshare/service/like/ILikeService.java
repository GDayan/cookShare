package com.dayan.cookshare.service.like;

import java.util.Optional;

public interface ILikeService {
    int likeRecipe (Long recipeId);
    int unLikeRecipe(Long recipeId);
    Long getLikeCount(Long recipeId);
}
