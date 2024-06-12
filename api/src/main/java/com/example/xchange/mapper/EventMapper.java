package com.example.xchange.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.xchange.dto.EventDTO;

import antlr.debug.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {


    EventDTO toEventDTO(Event event);

    List<EventDTO> toEventDTOs(List<Event> events);

}