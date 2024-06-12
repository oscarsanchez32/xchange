package com.example.xchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xchange.entity.Discount;

import java.util.Optional;

@Repository
public interface PromoRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findByCode(String code);
}
