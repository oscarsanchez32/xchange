package com.example.xchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xchange.dto.GameDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Game;
import com.example.xchange.mapper.GameMapper;
import com.example.xchange.payload.ApiResponse;
import com.example.xchange.payload.CartRequest;
import com.example.xchange.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;
    private final GameMapper gameMapper;

    @Operation(summary = "Add game to cart")
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> addGameToCart(@RequestBody CartRequest cartRequest){
        userService.addGameToCart(cartRequest.getGameId());
        ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "Item added to cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a users cart")
    @GetMapping("/cart")
    public ResponseEntity<List<GameDTO>> getUsersCart(){
        ApplicationUser user = userService.getCurrentUser();
        List<Game> cart = user.getCart();
        List<GameDTO> res = gameMapper.toGameDTOs(cart);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a cart item")
    @DeleteMapping("/cart/{gameId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("gameId") int gameId){
        userService.removeItemFromCart(gameId);
        ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "Removed item from cart.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
