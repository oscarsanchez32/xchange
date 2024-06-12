package com.example.xchange.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "promo")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "No puede estar vacío")
    @Column(name = "promo_code")
    private String code;

    @Min(value = 1, message = "Debe ser más de 0")
    @Column(name = "promo_amount")
    private double amount;

}
