package com.project.foodapp.review.services;

import java.util.List;

import com.project.foodapp.response.Response;
import com.project.foodapp.review.dtos.ReviewDTO;

public interface ReviewService {
    Response<ReviewDTO> createReview(ReviewDTO reviewDTO);

    Response<List<ReviewDTO>> getReviewsForMenu(Long menuId);

    Response<Double> getAverageRating(Long menuId);
}
