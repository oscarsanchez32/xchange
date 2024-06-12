package com.example.xchange.service.impl;
// package com.example.bookstorespringbootapi.service.impl;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.example.bookstorespringbootapi.dto.CommentCreateDTO;
// import com.example.bookstorespringbootapi.entity.ApplicationUser;
// import com.example.bookstorespringbootapi.entity.Comment;
// import com.example.bookstorespringbootapi.entity.Event;
// import com.example.bookstorespringbootapi.exception.InvalidInputException;
// import com.example.bookstorespringbootapi.exception.ResourceNotFoundException;
// import com.example.bookstorespringbootapi.repository.CommentRepository;
// import com.example.bookstorespringbootapi.service.CommentService;
// import com.example.bookstorespringbootapi.service.EventService;
// import com.example.bookstorespringbootapi.service.UserService;

// @Service
// public class CommentServiceImpl implements CommentService {

//     private final UserService userService;
//     private final EventService eventService;
//     private final CommentRepository commentRepository;

//     public CommentServiceImpl(UserService userService, EventService eventService, CommentRepository commentRepository) {
//         this.userService = userService;
//         this.eventService = eventService;
//         this.commentRepository = commentRepository;
//     }


//     @Override
//     public List<Comment> getAllCommentsForEvent(int eventId) {
//         return commentRepository.findAllByEventId(eventId);
//     }

//     @Override
//     public Comment getComment(int commentId) {
//         return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Cannot find Comment with id: " + commentId));
//     }

//     @Override
//     public Comment saveComment(CommentCreateDTO commentRequest, int eventId) {
//         ApplicationUser currentUser = userService.getCurrentUser();
//         Event event = eventService.getEventById(eventId);

//         if(commentExists(currentUser.getId(), eventId)){
//             throw new InvalidInputException("User has already posted comment for event id: " + eventId);
//         }

//         if(!currentUser.getUserInventory().contains(event)){
//             throw new InvalidInputException("User does not own event");
//         }

//         Comment comment = new Comment();
//         comment.setUser(currentUser);
//         comment.setEvent(event);
//         comment.setTitle(commentRequest.getTitle());
//         comment.setContent(commentRequest.getContent());
//         comment.setRating(commentRequest.getRating());

//         return commentRepository.save(comment);
//     }

//     public boolean commentExists(int userId, int eventId) {
//         return commentRepository.checkIfCommentExists(userId, eventId);
//     }

//     @Override
//     public void deleteComment(int commentId) {
//         commentRepository.deleteById(commentId);
//     }
// }

