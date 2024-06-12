package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.example.xchange.entity.enums.OrderStatus;
import com.example.xchange.entity.enums.PaymentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private int id;
    private double total;
    private PaymentType paymentType;
    private OrderStatus status;
    private String receiptUrl;
    private LocalDateTime createdAt;
    private List<GameDTO> orderItems;

}
