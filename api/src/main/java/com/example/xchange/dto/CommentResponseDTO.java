package com.example.xchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private int commentId;
    private String username;
    private String userImg;
    private String title;
    private String content;
    private String eventTitle;
    private String eventId;
    private LocalDateTime timestamp;
}
