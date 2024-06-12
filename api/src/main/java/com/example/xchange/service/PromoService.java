package com.example.xchange.service;


import com.example.xchange.dto.DiscountDTO;
import com.example.xchange.entity.Discount;

public interface PromoService {
    Discount getDiscount(String discount);

    Discount createDiscount(DiscountDTO discountDTO);

    void deleteDiscount(String discount);

}
