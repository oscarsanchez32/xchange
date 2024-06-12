// import { HttpErrorResponse } from '@angular/common/http';
// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute, Params } from '@angular/router';
// import { Book } from 'src/app/models/book.model';
// import { User } from 'src/app/models/user.model';
// import { AuthService } from 'src/app/services/auth.service';
// import { BookService } from 'src/app/services/book.service';
// import { CartService } from 'src/app/services/cart.service';
// import { ToastService } from 'src/app/services/toast.service';
// import { UserService } from 'src/app/services/user.service';

// @Component({
//   selector: 'app-event-detail',
//   templateUrl: './event-detail.component.html',
//   styleUrls: ['./event-detail.component.css']
// })
// export class EventDetailComponent implements OnInit {

//   book!: Book;
//   isLoading: boolean = true;
//   isError: boolean = false;
//   isEditing: boolean = false;
//   bookOwned: boolean = false;
//   currentUser!: User;

//   constructor(private bookService: BookService,
//     private router: Router,
//     private currentRoute: ActivatedRoute,
//     public authService: AuthService,
//     private cartService: CartService,
//     private userService: UserService,
//     private toast: ToastService) { }

//   ngOnInit(): void {
//     this.currentUser = this.authService.getCurrentUser();
//     this.currentRoute.params.subscribe({
//       next: (param: Params)=>{
//         this.getBookById(param['id']);
//         this.checkOwnership(this.authService.currentUser.username!, param['id']);
//       }
//     })
//   }

//   onCloseModal(){
//     this.isEditing = false;
//     this.ngOnInit();
//   }

//   getBookById(id: number){
//     this.bookService.getBookById(id).subscribe({
//       next: (response: Book)=>{
//         this.book = response;
//         this.isLoading = false;
//       },
//       error: (error: HttpErrorResponse)=>{
//         this.isError = true;
//         this.isLoading = false;
//         this.router.navigate(['/not-found']);
//       }
//     });
//   }

//   checkOwnership(username: string, id: number){
//     this.userService.itemOwnedByUser(username, id).subscribe({
//       next: res => {
//         this.bookOwned = true;
//       },
//       error: res => {
//         this.bookOwned = false;
//       }
//     });
//   }

//   onAddToCart(){
//     this.cartService.addToCart({bookId: this.currentRoute.snapshot.paramMap.get('id')}).subscribe({
//       next: res => {
//         this.cartService.cartUpdated.next(true);
//         this.toast.showToast('Item added', 'Item added to cart', 'success');
//       },
//       error: (error: HttpErrorResponse)=>{
//         if(error.error.message.indexOf('Item is already in cart') !== -1) this.toast.showToast('Error', 'Book is already in cart', 'error');
//         else this.toast.showToast('Error', 'Error adding to cart', 'error');
//       }
//     });
//   }


// }
