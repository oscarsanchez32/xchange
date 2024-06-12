package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCreateDTO {
    private int openerOwnedGameId;
    private int openerExchangeGameId;
}
