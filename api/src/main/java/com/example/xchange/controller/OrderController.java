package com.example.xchange.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

//    private final BookService bookService;
//    private final OrderService orderService;
//
//    @GetMapping("/order/{id}/cancel")
//    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
//
//        orderService.createOrder(orderCreateDTO);
//
//        ApiResponse res = new ApiResponse();
//        res.setStatus(HttpStatus.CREATED.value());
//        res.setMessage("Order created successfully.");
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }

}
