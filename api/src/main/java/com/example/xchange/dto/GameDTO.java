package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.xchange.entity.GameDetail;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameDTO {

    private int id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 4, max = 100, message = "El título debe tener entre 4 y 50 caracteres")
    private String title;

    @NotBlank(message = "El distribuidor no puede estar vacío")
    @Size(max = 100, message = "El nombre del editor debe tener 45 caracteres como máximo.")
    private String developera;

    @Min(value = 0)
    private double price;

    @Size(max = 512)
    private String shortDesc;
    private String imgPath;

    private GameDetail gameDetail;

    private String tags;
    private String ownerUsername;
}
