package com.example.xchange.controller;

import com.example.xchange.dto.*;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.ExchangeRequest;
import com.example.xchange.entity.Review;
import com.example.xchange.mapper.GameMapper;
import com.example.xchange.mapper.ExchangeMapper;
import com.example.xchange.mapper.OrderMapper;
import com.example.xchange.mapper.ReviewMapper;
import com.example.xchange.payload.*;
import com.example.xchange.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewMapper reviewMapper;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;
    private final GameMapper gameMapper;
    private final ExchangeMapper exchangeMapper;


    @Operation(summary = "Get current logged in user details")
    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentLoggedInUserDetails(){
        CurrentUserResponse res = new CurrentUserResponse(userService.getCurrentUser());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by username")
    @GetMapping("/{username}")
    public ResponseEntity<CurrentUserResponse> getUserDetails(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        return new ResponseEntity<>(new CurrentUserResponse(user), HttpStatus.OK);
    }

    @Operation(summary = "Get a users credits")
    @GetMapping("/{username}/credits")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<String> getUserCreditBalance(@PathVariable("username") String username) throws JsonProcessingException {
        ApplicationUser user = userService.getUserByUserName(username);
        Map<String, Double> res = new HashMap<>();
        res.put("amount", user.getCredits());
        return new ResponseEntity<>(objectMapper.writeValueAsString(res), HttpStatus.OK);
    }

    @Operation(summary = "Get a users reviews")
    @GetMapping("/{username}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsOfUser(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<Review> reviews = user.getReviews();
        List<ReviewResponseDTO> res = reviewMapper.toReviewResponseDTOs(reviews);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Check if item is owned by user")
    @GetMapping("/{username}/item-owned/{gameId}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> isItemOwnedByUser(@PathVariable("username") String username, @PathVariable("gameId") int gameId){
        ApplicationUser user = userService.getUserByUserName(username);
        if(userService.itemExistsInUserInventory(gameId, user.getId())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get a users orders")
    @GetMapping("/{username}/orders")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<List<OrderResponseDTO>> getUsersOrders(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<OrderResponseDTO> res = orderMapper.toOrderResponseDTOs(user.getOrders());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a users games")
    @GetMapping("/{username}/games")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<List<GameDTO>> getUsersGames(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<GameDTO> res = gameMapper.toGameDTOs(user.getUserInventory());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a users cart count")
    @GetMapping("/{username}/cart-count")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<String> getUserCartCount(@PathVariable("username") String username) throws JsonProcessingException {
        ApplicationUser user = userService.getUserByUserName(username);
        Map<String, Integer> map = new HashMap<>();
        map.put("cart_items", user.getCart().size());
        return new ResponseEntity<>(objectMapper.writeValueAsString(map), HttpStatus.OK);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("username") String username, @RequestBody UserUpdateDTO userUpdateDTO){
        ApiResponse res = new ApiResponse();
        System.out.println(userUpdateDTO);
        try{
            userService.updateUser(username, userUpdateDTO);
        } catch (Exception e){
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("User updated");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{username}/exchanges")
    @PreAuthorize("#username == authentication.name")
    @Operation(summary = "Get users exchanges")
    public ResponseEntity<List<ExchangeRequestDTO>> getUsersExchanges(@PathVariable("username") String username){
        ApplicationUser user = userService.getUserByUserName(username);
        List<ExchangeRequest> exchangeRequests = user.getExchangeRequests();
        List<ExchangeRequestDTO> exchangeRequestDTOS = exchangeMapper.toExchangeRequestDTOs(exchangeRequests);
        return new ResponseEntity<>(exchangeRequestDTOS, HttpStatus.OK);
    }

//    @GetMapping("/{username}/exchanges-accepted")
//    public ResponseEntity<>

}
