package com.example.xchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.xchange.dto.OrderCreateDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Discount;
import com.example.xchange.entity.Game;
import com.example.xchange.entity.Order;
import com.example.xchange.entity.enums.OrderStatus;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.repository.OrderRepository;
import com.example.xchange.service.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PromoService promoService;
    private final GameService gameService;

    @Override
    public Order createOrder(OrderCreateDTO orderCreateDTO) {

        ApplicationUser currentUser = userService.getCurrentUser();
        String discount = orderCreateDTO.getDiscount();
        double total = 0;

        // convert game ids into game objects
        List<Game> orderItems = orderCreateDTO.getCart()
                .stream()
                .map(gameService::getGameById)
                .collect(Collectors.toList());

        // check if user owns the games
        orderItems.forEach((game) -> {
            if(userService.itemExistsInUserInventory(game.getId(), currentUser.getId())){
                throw new InvalidInputException("Al usuario ya le pertenece el juego con id: " + game.getId());
            }
        });

        // calculate order total
        for(Game orderItem : orderItems){
            total += orderItem.getPrice();
        }

        // apply discount if exists
        if(discount != null && !discount.equals("")) {
            Discount dsc = promoService.getDiscount(discount);
            total -= dsc.getAmount();
            if(total < 0) total = 0;
            total = Math.round(total * 100);
            total = total/100;
        }

        // populate order object
        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.PENDIENTE);
        order.setPaymentType(orderCreateDTO.getPaymentType());
        order.setOrderItems(orderItems);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer id) {
        Optional<Order> optional = orderRepository.findById(id);
        return optional.orElseThrow(() -> new InvalidInputException("Could not find order with id - " + id));
    }

    @Override
    public Order fulfillOrder(Order order) {
        ApplicationUser user = order.getUser();

        // set order status to PAID
        order.setStatus(OrderStatus.PAGADO);

        // add games to user inventory
        order.getOrderItems().forEach((game) -> {
            user.getUserInventory().add(game);
        });

        // save order and clear users cart
        orderRepository.save(order);
        userService.clearCart(user.getId());
        return order;
    }

}
