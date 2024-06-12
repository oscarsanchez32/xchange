import { animate, state, style, transition, trigger } from '@angular/animations';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Review } from 'src/app/models/review.model';
import { AuthService } from 'src/app/services/auth.service';
import { ReviewService } from 'src/app/services/review.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {

  isLoading: boolean = true;
  isError: boolean = false;
  isErrorSavingReview: boolean = false;
  isAddingReview: boolean = false;
  isSavingReview: boolean = false;
  showToast: boolean = false;
  toastType: string = '';
  reviewForm: FormGroup = new FormGroup({});
  reviews: Review[] = [];
  currentGameId: number = 0;
  errorCode: number = 0;
  currentUsername: string = '';
  currentUrl: string = '';
  canReview: boolean = false;

  constructor(private reviewService: ReviewService, 
    private currentRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authService: AuthService,
    private toast: ToastService) { }

  ngOnInit(): void {
    this.currentUsername = this.authService.getCurrentLoggedInUsername();
    this.currentUrl = this.router.url;
    if(this.currentUrl.startsWith('/games')) {
      this.getAllReviewsForGame();
    }
    if(this.currentUrl.startsWith('/user')) this.getAllReviewsForUser();
    

    this.reviewForm = new FormGroup({
      title: new FormControl('', Validators.required),
      content: new FormControl('', Validators.required),
      rating: new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]),
    })
  }

  getAllReviewsForUser(){
    this.isLoading = true;
    this.currentRoute.parent!.params.subscribe({
      next: (param: Params) => {
        this.currentUsername = param['user'];
        this.userService.getUserReviews(this.currentUsername).subscribe({
          next: res => {
            this.reviews = res;
            this.isLoading = false;
          },
          error: error => {
            this.isError = true;
            this.isLoading = false;
          }
        });
      }
    });

  }

  getAllReviewsForGame(){
    this.isLoading = true;

    this.currentRoute.params.subscribe({
      next: (param: Params) => {this.currentGameId = param['id']}
    });

    this.reviewService.getReviewByGameId(this.currentGameId).subscribe({
      next: res => {
        this.reviews = res;
        this.isLoading = false;
        this.checkIfCurrentUserCreatedReview();
      },
      error: error => {
        this.isError = true;
      }
    })
  }

  onReviewSubmit(){
    this.isSavingReview = true;
    this.reviewService.addReview(this.currentGameId, this.reviewForm.value).subscribe({
      next: res => {
        this.isSavingReview = false;
        this.isAddingReview = false;
        this.ngOnInit();
        this.toast.showToast('Reseña creada', 'Tu reseña fue creada', 'success');
      },
      error: (error: HttpErrorResponse) => {
        this.isSavingReview = false;
        this.isErrorSavingReview = true;
        this.errorCode = error.status;
        this.toast.showToast('Error', 'No se pudo guardar la reseña', 'error');
      }
    });
  }

  onCloseReviewModal(){
    this.reviewForm.reset();
    this.isAddingReview = false;
    this.isErrorSavingReview = false;
  }

  checkIfCurrentUserCreatedReview(){
    for(let review of this.reviews){
      if(review.username === this.authService.getCurrentLoggedInUsername()) {
        this.canReview = false;
        return;
      }
    }
    this.canReview = true;
  }

  deleteReview(reviewId: number, gameId: number){
    this.reviewService.deleteReview(reviewId, gameId).subscribe({
      next: res => {
        this.ngOnInit();
        this.toast.showToast('Reseña eliminada', 'Tu reseña fue eliminada', 'success');
      }
    });
  }

}
