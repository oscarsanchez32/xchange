package com.example.xchange.service;

import java.util.List;

import com.example.xchange.dto.CommentCreateDTO;
import com.example.xchange.entity.Comment;

public interface CommentService {

    List<Comment> getAllCommentsForEvent(int eventId);

    Comment getComment(int commentId);

    Comment saveComment(CommentCreateDTO comment, int eventId);

    boolean commentExists(int userId, int eventId);

    void deleteComment(int commentId);
}