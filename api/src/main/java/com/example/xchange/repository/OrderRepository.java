package com.example.xchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xchange.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
