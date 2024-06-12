import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Game } from 'src/app/models/game.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { GameService } from 'src/app/services/game.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {
  game!: Game;
  isLoading: boolean = true;
  isError: boolean = false;
  isEditing: boolean = false;
  gameOwned: boolean = false;
  currentUser!: User;

  constructor(private gameService: GameService,
              private router: Router,
              private currentRoute: ActivatedRoute,
              public authService: AuthService,
              private cartService: CartService,
              private userService: UserService,
              private toast: ToastService) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.currentRoute.params.subscribe({
      next: (param: Params) => {
        this.getGameById(param['id']);
        this.checkOwnership(this.authService.currentUser.username!, param['id']);
      }
    });
  }

  onCloseModal() {
    this.isEditing = false;
    this.ngOnInit();
  }

  getGameById(id: number) {
    this.gameService.getGameById(id).subscribe({
      next: (response: Game) => {
        this.game = response;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.isError = true;
        this.isLoading = false;
        this.router.navigate(['/not-found']);
      }
    });
  }

  checkOwnership(username: string, id: number) {
    this.userService.itemOwnedByUser(username, id).subscribe({
      next: res => {
        this.gameOwned = true;
      },
      error: res => {
        this.gameOwned = false;
      }
    });
  }

  onAddToCart() {
    this.cartService.addToCart({ gameId: this.currentRoute.snapshot.paramMap.get('id') }).subscribe({
      next: res => {
        this.cartService.cartUpdated.next(true);
        this.toast.showToast('Juego añadido', 'Jueego añadido al carrito', 'success');
      },
      error: (error: HttpErrorResponse) => {
        if (error.error.message.indexOf('Item is already in cart') !== -1) this.toast.showToast('Error', 'El juego ya está en el carrito', 'error');
        else this.toast.showToast('Error', 'Ya tienes este producto en el carrito', 'error');
      }
    });
  }

  onDeleteGame() {
    if (confirm('Are you sure you want to delete this game?')) {
      this.gameService.deleteGameById(this.game.id).subscribe({
        next: () => {
          this.toast.showToast('Success', 'Game deleted successfully', 'success');
          this.router.navigate(['/']);
        },
        error: (error: HttpErrorResponse) => {
          this.toast.showToast('Error', 'Error deleting game', 'error');
        }
      });
    }
  }
}