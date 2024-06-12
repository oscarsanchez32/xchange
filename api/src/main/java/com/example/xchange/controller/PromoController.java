package com.example.xchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xchange.dto.DiscountDTO;
import com.example.xchange.entity.Discount;
import com.example.xchange.service.PromoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PromoController {
    private final PromoService promoService;

    @Operation(summary = "Get a discount by code")
    @GetMapping("/promo/{promo}")
    public ResponseEntity<?> getDiscount(@PathVariable("promo") String promoCode){
        Discount discount = promoService.getDiscount(promoCode);
        DiscountDTO res = new DiscountDTO(discount.getId(), discount.getCode(), discount.getAmount());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a discount")
    @PostMapping("/promo")
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO){
        Discount createdDiscount = promoService.createDiscount(discountDTO);
        DiscountDTO res = new DiscountDTO(createdDiscount.getId(), createdDiscount.getCode(), createdDiscount.getAmount());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a discount")
    @DeleteMapping("/promo/{code}")
    public ResponseEntity<?> deleteDiscount(@PathVariable("code") String code){
        promoService.deleteDiscount(code);
        return new ResponseEntity<>("Deleted promo", HttpStatus.OK);
    }
}
