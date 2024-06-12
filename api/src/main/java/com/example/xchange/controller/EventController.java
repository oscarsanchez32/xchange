package com.example.xchange.controller;
// package com.example.bookstorespringbootapi.controller;

// import javax.validation.Valid;

// import com.example.bookstorespringbootapi.service.EventService;

// import java.util.List;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api")
// @RequiredArgsConstructor
// public class EventController {

//     private final EventService eventService;
//     private final EventMapper eventMapper;
//     private final EventRepository eventRepository;
//     private UserService userService;

   
//     @Operation(summary = "Save a event")
//     @PostMapping("/events")
//     public ResponseEntity<EventDTO> saveEvent(@Valid @RequestBody EventDTO event){
//         Event savedEventevent.saveEvent(event);
//         EventDTO resevent.toEventDTO(savedEvent);
//         return new ResponseEntity<>(res, HttpStatus.CREATED);
//     }


//     @Operation(summary = "Get all events paged")
//     @GetMapping("/events")
//     public ResponseEntity<PagedResponseDTO<EventListDTO>> getEventsPaged(
//             @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
//             @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) throws InterruptedException {

//         PagedResponseDTO<BookListDTO> pagedResponseevent.getPagedResponse(page, size);

//         return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
//     }

//     @Operation(summary = "Get all books")
//     @GetMapping("/books/all")
//     public ResponseEntity<?> getAllBooks(){
//         List<Book> allBooksevent.getAllBooks();
//         List<BookListDTO> res = allBooks.stream().map(book -> {
//             return BookListDTO.builder()
//                     .id(book.getId())
//                     .bookDetail(book.getBookDetail())
//                     .price(book.getPrice())
//                     .reviewCount(book.getReviews().size())
//                     .imgPath(book.getImgPath())
//                     .author(book.getAuthor())
//                     .shortDesc(book.getShortDesc())
//                     .timesPurchased(0)
//                     .avgRevieevent.getAvgRatingForABook(book))
//                     .title(book.getTitle())
//                     .build();
//         }).collect(Collectors.toList());

//         return new ResponseEntity<>(res, HttpStatus.OK);
//     }

//     @Operation(summary = "Get book by id")
//     @GetMapping("/books/{id}")
//     public ResponseEntity<BookDTO> getBookById(@PathVariable("id") int id){
//         BookDTO resevent.toBookDevent.getBookById(id));
//         return new ResponseEntity<>(res, HttpStatus.OK);
//     }

//     @Operation(summary = "Update a book")
//     @PutMapping("/books")
//     public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO book){
//         Book savedBookevent.saveBook(book);
//         BookDTO resevent.toBookDTO(savedBook);
//         return new ResponseEntity<>(res, HttpStatus.OK);
//     }

//     @Operation(summary = "Search books")
//     @GetMapping("/books/search")
//     public ResponseEntity<List<BookDTO>> searchBookByTitle(@RequestParam String title){
//         List<Book> searchResultevent.searchByTitle(title);
//         List<BookDTO> resevent.toBookDTOs(searchResult);
//         return new ResponseEntity<>(res, HttpStatus.OK);
//     }

// }

