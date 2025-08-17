package com.dayan.cookshare.controller;

import com.dayan.cookshare.request.ReviewRequest;
import com.dayan.cookshare.service.review.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("/recipe/{recipeId}/review")
    public ResponseEntity<Void> rateRecipe(@RequestBody ReviewRequest request, @PathVariable Long recipeId){
        reviewService.addReview(recipeId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/{recipeId}/totalRatings")
    public ResponseEntity<Integer> getRateCount(@PathVariable Long recipeId){
        Integer totalRating = reviewService.getTotalReviews(recipeId);
        return ResponseEntity.ok(totalRating);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRating(@RequestParam Long ratingId, @RequestParam Long recipeId){
        reviewService.deleteReview(ratingId, recipeId);
        return ResponseEntity.ok("Successfully");
    }
}
