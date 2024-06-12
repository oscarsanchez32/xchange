package com.example.xchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.xchange.dto.ReviewResponseDTO;
import com.example.xchange.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.userName", target = "username")
    @Mapping(source = "id", target = "reviewId")
    @Mapping(source = "game.title", target = "gameTitle")
    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "user.userImg", target = "userImg")
    ReviewResponseDTO toReviewResponseDTO(Review review);

    List<ReviewResponseDTO> toReviewResponseDTOs(List<Review> reviews);

}
