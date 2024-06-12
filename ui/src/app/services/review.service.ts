import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../models/review.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }


  getReviewByGameId(id: number): Observable<Review[]>{
    return this.http.get<Review[]>(`${environment.baseUrl}/games/${id}/reviews`);
  }

  addReview(gameId: number, newReview: {title: string, content: string, rating: number}){
    return this.http.post<Review>(`${environment.baseUrl}/games/${gameId}/reviews`, newReview);
  }

  deleteReview(reviewId: number, gameId: number): Observable<any>{
    return this.http.delete(`${environment.baseUrl}/games/${gameId}/reviews/${reviewId}`)
  }

}
