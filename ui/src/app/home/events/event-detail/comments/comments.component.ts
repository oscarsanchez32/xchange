// import { HttpErrorResponse } from '@angular/common/http';
// import { Component, OnInit } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
// import { ActivatedRoute, Router, Params } from '@angular/router';
// import { Review } from 'src/app/models/review.model';
// import { AuthService } from 'src/app/services/auth.service';
// import { ReviewService } from 'src/app/services/review.service';
// import { ToastService } from 'src/app/services/toast.service';
// import { UserService } from 'src/app/services/user.service';

// @Component({
//   selector: 'app-comments',
//   templateUrl: './comments.component.html',
//   styleUrls: ['./comments.component.css']
// })
// export class CommentsComponent implements OnInit {

//   isLoading: boolean = true;
//   isError: boolean = false;
//   isErrorSavingReview: boolean = false;
//   isAddingReview: boolean = false;
//   isSavingReview: boolean = false;
//   showToast: boolean = false;
//   toastType: string = '';
//   reviewForm: FormGroup = new FormGroup({});
//   reviews: Review[] = [];
//   currentBookId: number = 0;
//   errorCode: number = 0;
//   currentUsername: string = '';
//   currentUrl: string = '';
//   canReview: boolean = false;

//   constructor(private reviewService: ReviewService, 
//     private currentRoute: ActivatedRoute,
//     private router: Router,
//     private userService: UserService,
//     private authService: AuthService,
//     private toast: ToastService) { }

//   ngOnInit(): void {
//     this.currentUsername = this.authService.getCurrentLoggedInUsername();
//     this.currentUrl = this.router.url;
//     if(this.currentUrl.startsWith('/books')) {
//       this.getAllReviewsForBook();
//     }
//     if(this.currentUrl.startsWith('/user')) this.getAllReviewsForUser();
    

//     this.reviewForm = new FormGroup({
//       title: new FormControl('', Validators.required),
//       content: new FormControl('', Validators.required),
//       rating: new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]),
//     })
//   }

//   getAllReviewsForUser(){
//     this.isLoading = true;
//     this.currentRoute.parent!.params.subscribe({
//       next: (param: Params) => {
//         this.currentUsername = param['user'];
//         this.userService.getUserReviews(this.currentUsername).subscribe({
//           next: res => {
//             this.reviews = res;
//             this.isLoading = false;
//           },
//           error: error => {
//             this.isError = true;
//             this.isLoading = false;
//           }
//         });
//       }
//     });

//   }

//   getAllReviewsForBook(){
//     this.isLoading = true;

//     this.currentRoute.params.subscribe({
//       next: (param: Params) => {this.currentBookId = param['id']}
//     });

//     this.reviewService.getReviewByBookId(this.currentBookId).subscribe({
//       next: res => {
//         this.reviews = res;
//         this.isLoading = false;
//         this.checkIfCurrentUserCreatedReview();
//       },
//       error: error => {
//         this.isError = true;
//       }
//     })
//   }

//   onReviewSubmit(){
//     this.isSavingReview = true;
//     this.reviewService.addReview(this.currentBookId, this.reviewForm.value).subscribe({
//       next: res => {
//         this.isSavingReview = false;
//         this.isAddingReview = false;
//         this.ngOnInit();
//         this.toast.showToast('Review created', 'Your review has been created', 'success');
//       },
//       error: (error: HttpErrorResponse) => {
//         this.isSavingReview = false;
//         this.isErrorSavingReview = true;
//         this.errorCode = error.status;
//         this.toast.showToast('Error', 'Cannot submit review', 'error');
//       }
//     });
//   }

//   onCloseReviewModal(){
//     this.reviewForm.reset();
//     this.isAddingReview = false;
//     this.isErrorSavingReview = false;
//   }

//   checkIfCurrentUserCreatedReview(){
//     for(let review of this.reviews){
//       if(review.username === this.authService.getCurrentLoggedInUsername()) {
//         this.canReview = false;
//         return;
//       }
//     }
//     this.canReview = true;
//   }

//   deleteReview(reviewId: number, bookId: number){
//     this.reviewService.deleteReview(reviewId, bookId).subscribe({
//       next: res => {
//         this.ngOnInit();
//         this.toast.showToast('Review deleted', 'Your review has been deleted', 'success');
//       }
//     });
//   }

// }
