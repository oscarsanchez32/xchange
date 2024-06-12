package com.example.xchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xchange.dto.ReviewCreateDTO;
import com.example.xchange.dto.ReviewResponseDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Review;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.mapper.ReviewMapper;
import com.example.xchange.payload.ApiResponse;
import com.example.xchange.service.ReviewService;
import com.example.xchange.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    @Operation(summary = "Get all reviews for a game")
    @GetMapping("/games/{gameId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsForGame(@PathVariable("gameId") int gameId){
        List<Review> reviews = reviewService.getAllReviewsForGame(gameId);
        List<ReviewResponseDTO> res = reviewMapper.toReviewResponseDTOs(reviews);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Save review")
    @PostMapping("/games/{gameId}/reviews")
    public ResponseEntity<ReviewResponseDTO> saveReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO, @PathVariable("gameId") int gameId){
        Review newReview = reviewService.saveReview(reviewCreateDTO, gameId);
        ReviewResponseDTO res = reviewMapper.toReviewResponseDTO(newReview);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a review")
    @DeleteMapping("/games/{gameId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int reviewId){
        Review review = reviewService.getReview(reviewId);
        ApplicationUser user = userService.getCurrentUser();
        if(review.getUser().getId() != user.getId()){
            throw new InvalidInputException("Cannot delete review. Unauthorized operation.");
        }
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Review deleted successfully."), HttpStatus.OK);
    }

}
