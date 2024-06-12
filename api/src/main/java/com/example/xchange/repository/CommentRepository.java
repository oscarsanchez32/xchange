package com.example.xchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.xchange.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByEventId(int eventId);

    @Query("SELECT CASE " +
            "WHEN COUNT(c) > 0 THEN TRUE " +
            "ELSE FALSE END " +
            "FROM Comment c " +
            "WHERE c.user.id = :userId AND c.event.id = :eventId")
    boolean checkIfCommentExists(int userId, int eventId);
}
