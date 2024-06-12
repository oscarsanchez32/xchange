import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Game } from '../models/game.model';
import { GameList } from '../models/GameList.model';
import { PagedResponse } from '../models/PagedResponse.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  
  private baseUrl: string = environment.baseUrl;
  
  constructor(private http: HttpClient) {}
    
  getGamesPaged(page: number, size: number): Observable<PagedResponse<GameList>> {
    return this.http.get<PagedResponse<GameList>>(`${this.baseUrl}/games`, {params: {page: page, size: size}});
  }

  getAllGames(): Observable<GameList[]> {
    return this.http.get<GameList[]>(`${this.baseUrl}/games/all`);
  }
  
  getGameById(id: number): Observable<Game> {
    return this.http.get<Game>(`${this.baseUrl}/games/${id}`);
  }

  getRandomGame(): Observable<Game> {
    return this.http.get<Game>(`${this.baseUrl}/rnd-game`);
  }
  
  saveGame(game: Game): Observable<Game> {
    console.log(game)
    return this.http.post<Game>(`${this.baseUrl}/games`, game);
  }
  
  deleteGameById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/games/${id}`);
  }

  updateGame(game: Game) {
    return this.http.put<Game>(`${this.baseUrl}/games`, game);
  }

  searchGame(searchString: string): Observable<GameList[]>{
    return this.http.get<GameList[]>(`${environment.baseUrl}/games/search?title=${searchString}`)
  }
  
}
