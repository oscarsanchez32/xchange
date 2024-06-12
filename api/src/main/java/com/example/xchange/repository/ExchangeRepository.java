package com.example.xchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.xchange.entity.ExchangeRequest;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeRequest, Integer> {
    @Query("SELECT CASE WHEN COUNT(er) = 1 THEN TRUE ELSE FALSE END " +
            "FROM ExchangeRequest er WHERE er.exchangeOpener.id = :userId AND er.openerOwnedGame.id = :gameId AND er.exchangeStatus = com.example.xchange.entity.enums.ExchangeStatus.EN_ESPERA")
    boolean checkIfExchangeRequestExists(int userId, int gameId);

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeStatus = com.example.xchange.entity.enums.ExchangeStatus.EN_ESPERA")
    List<ExchangeRequest> getAllOpenExchangeRequests();

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeOpener.id = :userId")
    List<ExchangeRequest> getAllExchangeRequestsMadeByUser(int userId);

    @Query("SELECT er FROM ExchangeRequest er WHERE er.exchangeCloser.id = :userId AND er.exchangeStatus = com.example.xchange.entity.enums.ExchangeStatus.EXITOSO")
    List<ExchangeRequest> getAllExchangeRequestsAcceptedByUser(int userId);
}
