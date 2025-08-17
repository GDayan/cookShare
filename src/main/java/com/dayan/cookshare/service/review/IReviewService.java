package com.dayan.cookshare.service.review;

import com.dayan.cookshare.model.Review;
import com.dayan.cookshare.request.ReviewRequest;

public interface IReviewService {
    void addReview(Long recipeId, ReviewRequest request);
    void deleteReview(Long recipeId, Long reviewId);
    int getTotalReviews(Long recipeId);
}
