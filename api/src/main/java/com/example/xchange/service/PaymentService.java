package com.example.xchange.service;

import com.example.xchange.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface PaymentService {
    void processStoreCreditPayment(Order order);

    Session generateCheckoutSession(Order order) throws StripeException, JsonProcessingException;

}
