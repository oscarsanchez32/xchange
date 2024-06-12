package com.example.xchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.xchange.dto.GameDTO;
import com.example.xchange.dto.GameListDTO;
import com.example.xchange.dto.PagedResponseDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Game;
import com.example.xchange.entity.Review;
import com.example.xchange.exception.ResourceNotFoundException;
import com.example.xchange.mapper.GameMapper;
import com.example.xchange.repository.GameRepository;
import com.example.xchange.service.GameService;
import com.example.xchange.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final UserService userService;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public PagedResponseDTO<GameListDTO> getPagedResponse(int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Game> paged = gameRepository.findAll(pageable);

        List<GameListDTO> content = paged.getContent().stream().map(game -> {
            return GameListDTO.builder()
                    .id(game.getId())
                    .gameDetail(game.getGameDetail())
                    .price(game.getPrice())
                    .reviewCount(game.getReviews().size())
                    .imgPath(game.getImgPath())
                    .developera(game.getDevelopera())
                    .shortDesc(game.getShortDesc())
                    .timesPurchased(game.getOwnedBy().size())
                    .avgReviews(getAvgRatingForAGame(game))
                    .productPurchased(checkIfProductPurchased(game))
                    .tags(game.getTags())
                    .title(game.getTitle())
                    .build();
        }).collect(Collectors.toList());

        PagedResponseDTO<GameListDTO> res = new PagedResponseDTO<>();
        res.setContent(content);
        res.setPage(page);
        res.setSize(size);
        res.setTotalElements(paged.getTotalElements());
        res.setTotalPages(paged.getTotalPages());
        res.setLast(paged.isLast());

        return res;
    }

    @Override
    public Game getGameById(int id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        gameOptional.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el juego con el id: " + id));
        return gameOptional.get();
    }

    @Override
    public Game saveGame(GameDTO gameDto) {
        Game game = null;
        if(gameDto.getId() != 0){
            game = getGameById(gameDto.getId());
        } else {
            game = new Game();
        }

        game.setGameDetail(gameDto.getGameDetail());
        game.setDevelopera(gameDto.getDevelopera());
        game.setShortDesc(gameDto.getShortDesc());
        game.setTitle(gameDto.getTitle());
        game.setPrice(gameDto.getPrice());
        game.setImgPath(gameDto.getImgPath());
        game.setTags(gameDto.getTags());

        return gameRepository.save(game);
    }

    @Override
    public void deleteGameById(int id){
        gameRepository.deleteById(id);
    }

    @Override
    public List<Game> searchByTitle(String title) {
        return gameRepository.searchByTitle(title);
    }

    @Override
    public double getAvgRatingForAGame(Game game) {
        List<Review> reviews = game.getReviews();
        int sum = 0;
        double avg = 0;
        for (Review r: reviews) {
            sum = sum + r.getRating();
        }
        avg = (double)sum/reviews.size();

        return Math.floor(Math.round(avg * 10.0) / 10.0);
    }

    private boolean checkIfProductPurchased(Game game){
        // check if request is anonymous
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        else {
            ApplicationUser currentUser = userService.getCurrentUser();
            return userService.itemExistsInUserInventory(game.getId(), currentUser.getId());
        }
    }

}
