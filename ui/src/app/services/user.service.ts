import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ExchangeRequest } from '../models/ExchangeRequest.model';
import { Order } from '../models/order.model';
import { Review } from '../models/review.model';
import { UpdateUserParams } from '../models/UpdateUserParams.model';
import { User } from '../models/user.model';
import { Game } from '../models/game.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  userUpdated = new Subject<boolean>();

  getUserByUsername(username: string): Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/user/${username}`);
  }

  getUserCredit(username: string): Observable<{amount: number}>{
    return this.http.get<{amount: number}>(`${environment.baseUrl}/user/${username}/credits`);
  }

  itemOwnedByUser(username: string, gameId: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}/user/${username}/item-owned/${gameId}`);
  }

  getUserReviews(username: string): Observable<Review[]>{
    return this.http.get<Review[]>(`${environment.baseUrl}/user/${username}/reviews`);
  }
 
  getUsersOrders(username: string): Observable<Order[]>{
    return this.http.get<Order[]>(`${environment.baseUrl}/user/${username}/orders`);
  }
  
  getUsersGames(username: string): Observable<Game[]>{
    return this.http.get<Game[]>(`${environment.baseUrl}/user/${username}/games`);
  }

  getUserCartCount(username: string): Observable<{cart_items: number}>{
    return this.http.get<{cart_items:number}>(`${environment.baseUrl}/user/${username}/cart-count`);
  }

  updateUser(username: string, params: UpdateUserParams): Observable<any>{
    return this.http.put(`${environment.baseUrl}/user/${username}`, params);
  }

  getUserExchanges(username: string): Observable<ExchangeRequest[]>{
    return this.http.get<ExchangeRequest[]>(`${environment.baseUrl}/user/${username}/exchanges`);
  }

  getRandomUser():Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/rnd-user`);
  }
}
