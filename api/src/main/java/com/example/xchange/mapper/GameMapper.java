package com.example.xchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.xchange.dto.GameDTO;
import com.example.xchange.entity.Game;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "imgPath", target = "imgPath")
    @Mapping(source = "tags", target = "tags")
    GameDTO toGameDTO(Game game);

    List<GameDTO> toGameDTOs(List<Game> games);

}
