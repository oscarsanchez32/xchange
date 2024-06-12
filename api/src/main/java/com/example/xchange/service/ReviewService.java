package com.example.xchange.service;

import java.util.List;

import com.example.xchange.dto.ReviewCreateDTO;
import com.example.xchange.entity.Review;

public interface ReviewService {

    List<Review> getAllReviewsForGame(int gameId);

    Review getReview(int reviewId);

    Review saveReview(ReviewCreateDTO review, int gameId);

    boolean reviewExists(int userId, int gameId);

    void deleteReview(int reviewId);
}
