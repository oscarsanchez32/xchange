package com.example.xchange.service;

import com.example.xchange.dto.OrderCreateDTO;
import com.example.xchange.entity.Order;

public interface OrderService {
    Order createOrder(OrderCreateDTO orderCreateDTO);
    Order getOrderById(Integer id);
    Order fulfillOrder(Order order);
}
