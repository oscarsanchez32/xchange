package com.example.xchange.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.xchange.entity.EventDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventDTO {

    private int id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 4, max = 100, message = "El título debe tener entre 4 to 50 caracteres")
    private String title;

    @Size(max = 512)
    private String shortDesc;
    private String imgPath;

    private EventDetail eventDetail;

    private String ownerUsername;
}

