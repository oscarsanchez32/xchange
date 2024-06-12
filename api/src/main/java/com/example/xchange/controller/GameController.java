package com.example.xchange.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xchange.dto.GameDTO;
import com.example.xchange.dto.GameListDTO;
import com.example.xchange.dto.PagedResponseDTO;
import com.example.xchange.entity.Game;
import com.example.xchange.mapper.GameMapper;
import com.example.xchange.repository.GameRepository;
import com.example.xchange.service.GameService;
import com.example.xchange.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;
    private final GameRepository gameRepository;
    private UserService userService;

    
    @Operation(summary = "Save a game")
    @PostMapping("/games")
    public ResponseEntity<GameDTO> saveGame(@Valid @RequestBody GameDTO game){
        Game savedGame = gameService.saveGame(game);
        System.out.println(game);
        GameDTO res = gameMapper.toGameDTO(savedGame);
        System.out.println(savedGame);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary="Delete a game")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameById(@PathVariable int id) {
        gameService.deleteGameById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all games paged")
    @GetMapping("/games")
    public ResponseEntity<PagedResponseDTO<GameListDTO>> getGemesPaged(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) throws InterruptedException {

        PagedResponseDTO<GameListDTO> pagedResponse = gameService.getPagedResponse(page, size);

        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all games")
    @GetMapping("/games/all")
    public ResponseEntity<?> getAllGames(){
        List<Game> allGames = gameService.getAllGames();
        List<GameListDTO> res = allGames.stream().map(game -> {
            return GameListDTO.builder()
                    .id(game.getId())
                    .gameDetail(game.getGameDetail())
                    .price(game.getPrice())
                    .reviewCount(game.getReviews().size())
                    .imgPath(game.getImgPath())
                    .developera(game.getDevelopera())
                    .shortDesc(game.getShortDesc())
                    .timesPurchased(0)
                    .avgReviews (gameService.getAvgRatingForAGame(game))
                    .title(game.getTitle())
                    .build();
        }).collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get game by id")
    @GetMapping("/games/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") int id){
        GameDTO res = gameMapper.toGameDTO (gameService.getGameById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Update a game")
    @PutMapping("/games")
    public ResponseEntity<GameDTO> updateGame(@Valid @RequestBody GameDTO game){
        Game savedGame = gameService.saveGame(game);
        GameDTO res = gameMapper.toGameDTO(savedGame);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Search games")
    @GetMapping("/games/search")
    public ResponseEntity<List<GameDTO>> searchGameByTitle(@RequestParam String title){
        List<Game> searchResult = gameService.searchByTitle(title);
        List<GameDTO> res = gameMapper.toGameDTOs(searchResult);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
