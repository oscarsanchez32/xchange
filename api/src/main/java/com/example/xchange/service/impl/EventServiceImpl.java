package com.example.xchange.service.impl;
// package com.example.bookstorespringbootapi.service.impl;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.example.bookstorespringbootapi.dto.EventDTO;
// import com.example.bookstorespringbootapi.entity.ApplicationUser;
// import com.example.bookstorespringbootapi.entity.Comment;
// import com.example.bookstorespringbootapi.repository.EventRepository;
// import com.example.bookstorespringbootapi.service.EventService;
// import com.example.bookstorespringbootapi.service.UserService;

// import antlr.debug.Event;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class EventServiceImpl implements EventService {

//     private final EventRepository eventRepository;
//     private final EventMapper eventMapper;
//     private final UserService userService;

//     @Override
//     public List<Event> getAllEvents() {
//         return eventRepository.findAll();
//     }

//     @Override
//     public PagedResponseDTO<EventListDTO> getPagedResponse(int page, int size){
//         Pageable pageable = PageRequest.of(page-1, size);
//         Page<Book> paged = bookRepository.findAll(pageable);

//         List<BookListDTO> content = paged.getContent().stream().map(book -> {
//             return BookListDTO.builder()
//                     .id(book.getId())
//                     .bookDetail(book.getBookDetail())
//                     .price(book.getPrice())
//                     .reviewCount(book.getReviews().size())
//                     .imgPath(book.getImgPath())
//                     .author(book.getAuthor())
//                     .shortDesc(book.getShortDesc())
//                     .timesPurchased(book.getOwnedBy().size())
//                     .avgReviews(getAvgRatingForABook(book))
//                     .productPurchased(checkIfProductPurchased(book))
//                     .tags(book.getTags())
//                     .title(book.getTitle())
//                     .build();
//         }).collect(Collectors.toList());

//         PagedResponseDTO<BookListDTO> res = new PagedResponseDTO<>();
//         res.setContent(content);
//         res.setPage(page);
//         res.setSize(size);
//         res.setTotalElements(paged.getTotalElements());
//         res.setTotalPages(paged.getTotalPages());
//         res.setLast(paged.isLast());

//         return res;
//     }

//     @Override
//     public Event getEventById(int id) {
//         Optional<Event> eventOptional = eventRepository.findById(id);
//         eventOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot find Event with id: " + id));
//         return eventOptional.get();
//     }

//     @Override
//     public Event saveEvent(EventDTO eventDto) {
//         Event event = null;
//         if(eventDto.getId() != 0){
//             event = getEventById(eventDto.getId());
//         } else {
//             event = new Event();
//         }

//         book.setBookDetail(bookDto.getBookDetail());
//         book.setAuthor(bookDto.getAuthor());
//         book.setShortDesc(bookDto.getShortDesc());
//         book.setTitle(bookDto.getTitle());
//         book.setPrice(bookDto.getPrice());
//         book.setImgPath(bookDto.getImgPath());
//         book.setTags(bookDto.getTags());

//         return eventRepository.save(event);
//     }

//     @Override
//     public List<Event> searchByTitle(String title) {
//         return eventRepository.searchByTitle(title);
//     }

//     @Override
//     public double getAvgRatingForAEvent(Event event) {
//         List<Comment> comments = event.getReviews();
//         int sum = 0;
//         double avg = 0;
//         for (Comment c: comments) {
//             sum = sum + c.getRating();
//         }
//         avg = (double)sum/comments.size();

//         return Math.floor(Math.round(avg * 10.0) / 10.0);
//     }

//     private boolean checkIfProductPurchased(Event event){
//         // check if request is anonymous
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if(authentication instanceof AnonymousAuthenticationToken) return false;
//         else {
//             ApplicationUser currentUser = userService.getCurrentUser();
//             return userService.itemExistsInUserInventory(event.getId(), currentUser.getId());
//         }
//     }

// }

