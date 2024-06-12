package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreateDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String title;
    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(max = 500, message = "La longitud debe tener un máximo de 500 caracteres")
    private String content;
  
}
