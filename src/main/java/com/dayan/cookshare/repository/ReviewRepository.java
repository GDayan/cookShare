package com.dayan.cookshare.repository;

import com.dayan.cookshare.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    int countAllByRecipeId(Long recipeId);

    Optional<Review> findByRecipeIdAndUserId(Long recipeId, Long id);

    Review findByRecipeId(Long id);

    List<Review> findAllByRecipeId(Long id);
}
