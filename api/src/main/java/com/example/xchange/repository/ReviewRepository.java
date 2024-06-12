package com.example.xchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.xchange.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByGameId(int gameId);

    @Query("SELECT CASE " +
            "WHEN COUNT(r) > 0 THEN TRUE " +
            "ELSE FALSE END " +
            "FROM Review r " +
            "WHERE r.user.id = :userId AND r.game.id = :gameId")
    boolean checkIfReviewExists(int userId, int gameId);
}
