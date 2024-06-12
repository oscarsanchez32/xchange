// import { HttpErrorResponse } from '@angular/common/http';
// import { Component, OnInit } from '@angular/core';
// import { NgForm } from '@angular/forms';
// import { ActivatedRoute } from '@angular/router';
// import { BookList } from 'src/app/models/BookList.model';
// import { PagedResponse } from 'src/app/models/PagedResponse.model';
// import { User } from 'src/app/models/user.model';
// import { AuthService } from 'src/app/services/auth.service';
// import { BookService } from 'src/app/services/book.service';

// @Component({
//   selector: 'app-event-list',
//   templateUrl: './event-list.component.html',
//   styleUrls: ['./event-list.component.css']
// })
// export class EventListComponent implements OnInit {
//   books: BookList[] = [];
//   isError: boolean = false;
//   isLoading: boolean = true;
//   isAdding: boolean = false;
//   currentPage: number = 1;
//   pageSize: number = 5;
//   totalPages: number = 0;
//   isLast: boolean = false;
//   totalResults: number = 0;
//   currentUser: User = new User();
//   loggedIn: boolean = false;
//   filteredBooks: BookList[] = [];
//   filterTag: string = '';


//   constructor(private bookService: BookService,
//     private currentRoute: ActivatedRoute,
//     private auth: AuthService) { }

//   ngOnInit(): void {
//     this.getAllBooks(this.currentPage, this.pageSize);
//     if(this.auth.isLoggedIn()) {
//       this.currentUser = this.auth.getCurrentUser();
//       this.loggedIn = true;
//       this.filteredBooks = this.books;
//     }
//   }

//   getAllBooks(page: number, size: number){
//     this.isLoading = true;
//     this.bookService.getBooksPaged(page, size).subscribe({
//       next: (res: PagedResponse<BookList>)=>{
//         this.books = res.content;
//         this.totalPages = res.totalPages;
//         this.isLast = res.last;
//         this.totalResults = res.totalElements;
//         this.isError = false;
//         this.isLoading = false;
//       },
//       error: (error: HttpErrorResponse)=>{
//         this.isError = true;
//         this.isLoading = false;
//       }
//     })
//   }

//   selectPage(page: number){
//     if(this.isLoading || page === this.currentPage) return;
//     this.currentPage = page;
//     this.getAllBooks(this.currentPage, this.pageSize);
//   }

//   nextPage(){
//     if(this.isLoading) return;
//     if(this.isLast) return;
//     this.getAllBooks(++this.currentPage, this.pageSize);
//   }

//   previousPage(){
//     if(this.isLoading) return;
//     if(this.currentPage === 1) return;
//     this.getAllBooks(--this.currentPage, this.pageSize);
//   }

//   onCloseModal(){
//     this.isAdding = false;
//   }

//   onOpenModal() {
//     this.isAdding = true;
//     // window.scrollTo(0, 0);
//   }

//   onSearchSubmit(form: NgForm){
//     this.bookService.searchBook(form.value.search).subscribe({
//       next: res => {
//         this.books = res;
//       }
//     });
//   }
//   filterByTag(): void {
//     if (this.filterTag) {
//       this.filteredBooks = this.books.filter(book => 
//         book.tags?.split(',').includes(this.filterTag)
//       );
//     } else {
//       this.filteredBooks = this.books;
//     }
//   }

// }
