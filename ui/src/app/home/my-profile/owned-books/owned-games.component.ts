import { Component, OnInit } from '@angular/core';
import { Game } from 'src/app/models/game.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-owned-games',
  templateUrl: './owned-games.component.html',
  styleUrls: ['./owned-games.component.css']
})
export class OwnedGamesComponent implements OnInit {

  ownedGames: Game[] = [];

  constructor(private authService: AuthService,
    private userService: UserService) { }

    isLoading = false;

  ngOnInit(): void {
    this.isLoading = true;
    this.userService.getUsersGames(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.isLoading = false;
        this.ownedGames = res;
      }
    });
  }

}
