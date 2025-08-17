package com.dayan.cookshare.repository;

import com.dayan.cookshare.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{
    Long countByRecipeId(Long recipeId);
    boolean existsByRecipeId(Long id);

    Optional<Like> findFirstByRecipeId(Long recipeId);
}
