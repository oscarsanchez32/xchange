package com.example.xchange.service.impl;

import org.springframework.stereotype.Service;

import com.example.xchange.dto.ReviewCreateDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Game;
import com.example.xchange.entity.Review;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.exception.ResourceNotFoundException;
import com.example.xchange.repository.ReviewRepository;
import com.example.xchange.service.GameService;
import com.example.xchange.service.ReviewService;
import com.example.xchange.service.UserService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final GameService gameService;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(UserService userService, GameService gameService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.gameService = gameService;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<Review> getAllReviewsForGame(int gameId) {
        return reviewRepository.findAllByGameId(gameId);
    }

    @Override
    public Review getReview(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("No se puede encontrar Review con id: " + reviewId));
    }

    @Override
    public Review saveReview(ReviewCreateDTO reviewRequest, int gameId) {
        ApplicationUser currentUser = userService.getCurrentUser();
        Game game = gameService.getGameById(gameId);

        if(reviewExists(currentUser.getId(), gameId)){
            throw new InvalidInputException("El usuario ya ha posteado la review para el libro con id: " + gameId);
        }

        if(!currentUser.getUserInventory().contains(game)){
            throw new InvalidInputException("Al usuario no le pertenece ningún libro");
        }

        Review review = new Review();
        review.setUser(currentUser);
        review.setGame(game);
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());

        return reviewRepository.save(review);
    }

    public boolean reviewExists(int userId, int gameId) {
        return reviewRepository.checkIfReviewExists(userId, gameId);
    }

    @Override
    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
