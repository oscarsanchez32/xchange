package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.xchange.entity.enums.ExchangeStatus;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequestDTO {
    private int id;
    private String exchangeOpener;
    private String exchangeCloser;
    private int openerOwnedGameId;
    private String openerOwnedGameTitle;
    private int openerExchangeGameId;
    private String openerExchangeGameTitle;
    private ExchangeStatus exchangeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private Boolean canExchange = null;
}
