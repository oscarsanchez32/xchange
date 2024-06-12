import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { Game } from '../models/game.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl: string = environment.baseUrl;
  cartUpdated: Subject<boolean> = new Subject<boolean>();
  
  constructor(private http: HttpClient) {}

  getUserCart(): Observable<Game[]>{
    return this.http.get<Game[]>(`${this.baseUrl}/cart`);
  }

  removeFromCart(gameId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/cart/${gameId}`);
  }

  addToCart(gameId: object): Observable<any>{
    return this.http.post<any>(`${this.baseUrl}/cart`, gameId);
  }
    
}
