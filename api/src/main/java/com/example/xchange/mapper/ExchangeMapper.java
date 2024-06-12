package com.example.xchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.xchange.dto.ExchangeRequestDTO;
import com.example.xchange.entity.ExchangeRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    @Mapping(source = "exchangeOpener.userName", target = "exchangeOpener")
    @Mapping(source = "exchangeCloser.userName", target = "exchangeCloser")
    @Mapping(source = "openerOwnedGame.id", target = "openerOwnedGameId")
    @Mapping(source = "openerOwnedGame.title", target = "openerOwnedGameTitle")
    @Mapping(source = "openerExchangeGame.id", target = "openerExchangeGameId")
    @Mapping(source = "openerExchangeGame.title", target = "openerExchangeGameTitle")
    ExchangeRequestDTO toExchangeRequestDTO(ExchangeRequest exchangeRequest);
    List<ExchangeRequestDTO> toExchangeRequestDTOs(List<ExchangeRequest> exchangeRequests);
}
