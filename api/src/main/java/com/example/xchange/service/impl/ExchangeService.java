package com.example.xchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.xchange.dto.ExchangeCreateDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.ExchangeRequest;
import com.example.xchange.entity.Game;
import com.example.xchange.entity.enums.ExchangeStatus;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.repository.ExchangeRepository;
import com.example.xchange.service.GameService;
import com.example.xchange.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final UserService userService;
    private final GameService gameService;
    private final ExchangeRepository exchangeRepository;

    public void createNewExchange(ExchangeCreateDTO exchangeCreateDTO){
        ApplicationUser currentUser = userService.getCurrentUser();

        // throw ex if user owns target game
        if(userService.itemExistsInUserInventory(exchangeCreateDTO.getOpenerExchangeGameId(), currentUser.getId())){
            throw new InvalidInputException("Target game is owned by user");
        }

        // throw ex if user does not own source game
        if(!userService.itemExistsInUserInventory(exchangeCreateDTO.getOpenerOwnedGameId(), currentUser.getId())){
            throw new InvalidInputException("User does not own the given owned game");
        }

        // throw ex if exchange exists for source game
        if(checkIfExchangeRequestExists(currentUser.getId(), exchangeCreateDTO.getOpenerOwnedGameId())){
            throw new InvalidInputException("Este Exchange ya existe");
        }

        ExchangeRequest request = new ExchangeRequest();
        Game openerOwnedGame = gameService.getGameById(exchangeCreateDTO.getOpenerOwnedGameId());
        Game openerExchangeGame = gameService.getGameById(exchangeCreateDTO.getOpenerExchangeGameId());

        request.setExchangeOpener(userService.getCurrentUser());
        request.setOpenerOwnedGame(openerOwnedGame);
        request.setOpenerExchangeGame(openerExchangeGame);
        request.setExchangeStatus(ExchangeStatus.EN_ESPERA);

        exchangeRepository.save(request);

    }

    public boolean checkIfExchangeRequestExists(int userId, int openerOwnedGame){
        return exchangeRepository.checkIfExchangeRequestExists(userId, openerOwnedGame);
    }

    public List<ExchangeRequest> getAllOpenExchangeRequests() {
        return exchangeRepository.getAllOpenExchangeRequests();
    }

    public ExchangeRequest getExchangeById(int id){
        Optional<ExchangeRequest> exchangeOp = exchangeRepository.findById(id);
        return exchangeOp.orElseThrow(() -> {throw new InvalidInputException("No se pudo encontrar el Exchange con id "+ id);});
    }

    public void cancelExchangeRequest(int exId) {
        ExchangeRequest exchangeRequest = getExchangeById(exId);
        exchangeRequest.setExchangeStatus(ExchangeStatus.CERRADO);
        exchangeRequest.setExchangeCloser(exchangeRequest.getExchangeOpener());
        exchangeRequest.setClosedAt(LocalDateTime.now());

        exchangeRepository.save(exchangeRequest);
    }

    public void processExchangeRequest(int exId) {
        ApplicationUser closerUser = userService.getCurrentUser();
        ExchangeRequest exchangeRequest = getExchangeById(exId);

        // check if closer owns the openers exchange game
        if(!userService.itemExistsInUserInventory(exchangeRequest.getOpenerExchangeGame().getId(), closerUser.getId())){
            // throw exception if closer does not own game
            throw new InvalidInputException("Closer does not own the opener exchange game");
        }
        // check if closer owns the openers owned game, prevents adding duplicate games to users inventory
        if(userService.itemExistsInUserInventory(exchangeRequest.getOpenerOwnedGame().getId(), closerUser.getId())){
            throw new InvalidInputException("Closer already owns the opener owned game - exchange not possible.");
        }

        userService.removeFromInventory(exchangeRequest.getExchangeOpener().getId(), exchangeRequest.getOpenerOwnedGame().getId());
        userService.removeFromInventory(closerUser.getId(), exchangeRequest.getOpenerExchangeGame().getId());

        userService.addToInventory(exchangeRequest.getExchangeOpener().getId(), exchangeRequest.getOpenerExchangeGame().getId());
        userService.addToInventory(closerUser.getId(), exchangeRequest.getOpenerOwnedGame().getId());

        exchangeRequest.setExchangeCloser(closerUser);
        exchangeRequest.setExchangeStatus(ExchangeStatus.EXITOSO);
        exchangeRequest.setClosedAt(LocalDateTime.now());

        exchangeRepository.save(exchangeRequest);
    }
}
