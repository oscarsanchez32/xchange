package com.example.xchange.service;

import java.util.List;

import com.example.xchange.dto.EventDTO;
import com.example.xchange.dto.EventListDTO;
import com.example.xchange.dto.PagedResponseDTO;

import antlr.debug.Event;

public interface EventService {

    List<Event> getAllEvents();

    Event getEventById(int id);

    Event saveEvent(EventDTO event);

    List<Event> searchByTitle(String title);


    double getAvgRatingForAEvent(Event event);

    PagedResponseDTO<EventListDTO> getPagedResponse(int page, int size);
}
