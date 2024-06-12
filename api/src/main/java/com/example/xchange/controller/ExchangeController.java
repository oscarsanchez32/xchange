package com.example.xchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xchange.dto.ExchangeCreateDTO;
import com.example.xchange.dto.ExchangeRequestDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.ExchangeRequest;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.mapper.ExchangeMapper;
import com.example.xchange.payload.ApiResponse;
import com.example.xchange.service.UserService;
import com.example.xchange.service.impl.ExchangeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final UserService userService;
    private final ExchangeMapper exchangeMapper;

    @Operation(summary = "Create a new exchange request")
    @PostMapping("/exchange")
    public ResponseEntity<ApiResponse> createNewExchange(@RequestBody ExchangeCreateDTO exchangeCreateDTO){
        ApiResponse res = new ApiResponse();
        ApplicationUser currentUser = userService.getCurrentUser();

        exchangeService.createNewExchange(exchangeCreateDTO);
        res.setMessage("Exchange created");
        res.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all exchanges")
    @GetMapping("/exchange")
    public ResponseEntity<List<ExchangeRequestDTO>> getAllExchanges(){
        ApplicationUser currentUser = userService.getCurrentUser();
        List<ExchangeRequest> allExchanges = exchangeService.getAllOpenExchangeRequests();
        List<ExchangeRequestDTO> res = exchangeMapper.toExchangeRequestDTOs(allExchanges);

        for(ExchangeRequestDTO e : res){
            if(userService.itemExistsInUserInventory(e.getOpenerExchangeGameId(), currentUser.getId()) && !userService.itemExistsInUserInventory(e.getOpenerOwnedGameId(), currentUser.getId())){
                e.setCanExchange(true);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Cancel an exchange")
    @GetMapping("/exchange/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelExchange(@PathVariable("id") int id){
        ApplicationUser currentUser = userService.getCurrentUser();
        ExchangeRequest ex = exchangeService.getExchangeById(id);
        if(!ex.getExchangeOpener().getUserName().equals(currentUser.getUserName())){
            throw new InvalidInputException("Unauthorized operation");
        }

        exchangeService.cancelExchangeRequest(id);
        ApiResponse res = new ApiResponse();
        res.setMessage("Success");
        res.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Process exchange request")
    @GetMapping("/exchange/{id}/process")
    public ResponseEntity<ApiResponse> processExchange(@PathVariable("id") int exId){
        exchangeService.processExchangeRequest(exId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Success");
        res.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
