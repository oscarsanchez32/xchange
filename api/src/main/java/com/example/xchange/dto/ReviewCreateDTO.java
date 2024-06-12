package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreateDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String title;
    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(max = 500, message = "La longitud tiene 500 caracteres como máximo")
    private String content;
    @Min(value = 1, message = "La puntuación debe estar entre 1 - 5")
    @Max(value = 5, message = "La puntuación debe estar entre 1 - 5")
    private int rating;
}
