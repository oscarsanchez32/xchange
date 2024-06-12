package com.example.xchange.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import com.example.xchange.entity.enums.ExchangeStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exchange_board")
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "exchange_opener_user_id")
    private ApplicationUser exchangeOpener;

    @ManyToOne
    @JoinColumn(name = "opener_owned_game_id")
    private Game openerOwnedGame;

    @ManyToOne
    @JoinColumn(name = "opener_exchange_game_id")
    private Game openerExchangeGame;

    @ManyToOne
    @JoinColumn(name = "exchange_closer_user_id")
    private ApplicationUser exchangeCloser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ExchangeStatus exchangeStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

}

