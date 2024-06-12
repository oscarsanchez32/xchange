package com.example.xchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.xchange.dto.OrderResponseDTO;
import com.example.xchange.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDTO toOrderResponseDTO(Order order);
    List<OrderResponseDTO> toOrderResponseDTOs(List<Order> orders);
}
