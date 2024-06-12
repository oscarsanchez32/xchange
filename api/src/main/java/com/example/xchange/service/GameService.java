package com.example.xchange.service;


import com.example.xchange.dto.GameDTO;
import com.example.xchange.dto.GameListDTO;
import com.example.xchange.dto.PagedResponseDTO;
import com.example.xchange.entity.Game;

import java.util.List;

public interface GameService {

    List<Game> getAllGames();

    Game getGameById(int id);

    Game saveGame(GameDTO game);

    void deleteGameById(int id);

    List<Game> searchByTitle(String title);

    double getAvgRatingForAGame(Game game);

    PagedResponseDTO<GameListDTO> getPagedResponse(int page, int size);
}
