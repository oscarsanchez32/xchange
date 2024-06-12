package com.example.xchange.dto;

import com.example.xchange.entity.EventDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventListDTO{
    private int id;
    private String title;

    private String shortDesc;
    private String imgPath;
    private EventDetail eventDetail;
    private int commentCount;
    private double avgComments;
    
}