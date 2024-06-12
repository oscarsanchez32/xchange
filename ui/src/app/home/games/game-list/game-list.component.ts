import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Form, NgForm } from '@angular/forms';
import { PagedResponse } from 'src/app/models/PagedResponse.model';
import { GameList } from 'src/app/models/GameList.model';
import { GameService } from 'src/app/services/game.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {

  games: GameList[] = [];
  isError: boolean = false;
  isLoading: boolean = true;
  isAdding: boolean = false;
  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = 0;
  isLast: boolean = false;
  totalResults: number = 0;
  currentUser: User = new User();
  loggedIn: boolean = false;
  filteredGames: GameList[] = [];
  filterTag: string = '';


  constructor(private gameService: GameService,
    private currentRoute: ActivatedRoute,
    private auth: AuthService) { }

  ngOnInit(): void {
    this.getAllGames(this.currentPage, this.pageSize);
    if(this.auth.isLoggedIn()) {
      this.currentUser = this.auth.getCurrentUser();
      this.loggedIn = true;
      this.filteredGames = this.games;
    }
  }

  getAllGames(page: number, size: number){
    this.isLoading = true;
    this.gameService.getGamesPaged(page, size).subscribe({
      next: (res: PagedResponse<GameList>)=>{
        this.games = res.content;
        this.totalPages = res.totalPages;
        this.isLast = res.last;
        this.totalResults = res.totalElements;
        this.isError = false;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse)=>{
        this.isError = true;
        this.isLoading = false;
      }
    })
  }

  selectPage(page: number){
    if(this.isLoading || page === this.currentPage) return;
    this.currentPage = page;
    this.getAllGames(this.currentPage, this.pageSize);
  }

  nextPage(){
    if(this.isLoading) return;
    if(this.isLast) return;
    this.getAllGames(++this.currentPage, this.pageSize);
  }

  previousPage(){
    if(this.isLoading) return;
    if(this.currentPage === 1) return;
    this.getAllGames(--this.currentPage, this.pageSize);
  }

  onCloseModal(){
    this.isAdding = false;
  }

  onOpenModal() {
    this.isAdding = true;
    // window.scrollTo(0, 0);
  }

  onSearchSubmit(form: NgForm){
    this.gameService.searchGame(form.value.search).subscribe({
      next: res => {
        this.games = res;
      }
    });
  }
  filterByTag(): void {
    if (this.filterTag) {
      this.filteredGames = this.games.filter(game => 
        game.tags?.split(',').includes(this.filterTag)
      );
    } else {
      this.filteredGames = this.games;
    }
  }

}
