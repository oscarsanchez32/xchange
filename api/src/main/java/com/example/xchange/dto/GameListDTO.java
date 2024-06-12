package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.example.xchange.entity.GameDetail;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameListDTO{
    private int id;
    private String title;
    private String developera;
    private double price;
    private String shortDesc;
    private String imgPath;
    private GameDetail gameDetail;
    private boolean productPurchased;
    private int reviewCount;
    private double avgReviews;
    private int timesPurchased;
    private String tags;
}