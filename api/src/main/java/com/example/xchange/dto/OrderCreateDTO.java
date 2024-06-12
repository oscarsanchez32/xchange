package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.xchange.entity.enums.PaymentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    private List<Integer> cart;
    private String discount;
    private PaymentType paymentType;
}
