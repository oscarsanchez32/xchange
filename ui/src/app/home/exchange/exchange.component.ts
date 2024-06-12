import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { ExchangeRequest } from 'src/app/models/ExchangeRequest.model';
import { Game } from 'src/app/models/game.model';
import { AuthService } from 'src/app/services/auth.service';
import { ExchangeService } from 'src/app/services/exchange.service';
import { GameService } from 'src/app/services/game.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';
import { ToastParams } from 'src/app/toast/Toast.model';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {

  constructor(private exchangeService: ExchangeService,
    private auth: AuthService,
    private userService: UserService,
    private gameService: GameService,
    private toast: ToastService) { }

  exchanges: ExchangeRequest[] = [];
  currentUser: string = this.auth.getCurrentLoggedInUsername();
  isCreatingNewExchange: boolean = false;
  userGames: Game[] = [];
  allGames: Game[] = [];
  exchangeForm: FormGroup = new FormGroup({})
  isError: boolean = false;
  isLoading: boolean = false;

  ngOnInit(): void {
    this.getAllOpenExchanges();
    this.getAllUserGames();
    this.getAllGames();

    this.exchangeForm = new FormGroup({
      openerOwnedGameId: new FormControl(null, Validators.required),
      openerExchangeGameId: new FormControl(null, Validators.required)
    })
  }

  getAllOpenExchanges(){
    this.isLoading = true;
    this.exchangeService.getAllOpenExchanges().subscribe({
      next: (res: ExchangeRequest[]) => {
        this.isLoading = false;
        this.exchanges = res;
      }
    });
  }

  getAllUserGames(){
    this.userService.getUsersGames(this.auth.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.userGames = res;
      }
    });
  }

  getAllGames(){
    this.gameService.getAllGames().subscribe({
      next: res => {
        this.allGames = res;
      }
    });
  }

  onCloseModal(){
    this.isCreatingNewExchange = false;
    this.exchangeForm.reset();
  }

  onSubmit(){
    this.isLoading = true;
    this.exchangeService.createExchange(this.exchangeForm.value).subscribe({
      next: res => {
        this.isLoading = false;
        this.onCloseModal();
        this.toast.showToast('Exchange created', 'Your exchange has been created', 'success');
        this.getAllOpenExchanges();
      },
      error: (res:HttpErrorResponse) => {
        this.isLoading = false;
        this.toast.showToast('Error', 'Could not create exchange', 'error');
      }
    });
  }

  onCancelExchange(id: number){
    this.isLoading = true;
    this.exchangeService.cancelExchange(id).subscribe({
      next: res => {
        this.isLoading = false;
        this.getAllOpenExchanges();
        this.toast.showToast('Success', 'Exchange request cancelled', 'success');
      },
      error: res => {
        this.isLoading = false;
        this.toast.showToast('Error', 'Could not cancel', 'error');
      }
    });
  }

  processExchange(id: number){
    this.isLoading = true;
    this.exchangeService.processExchange(id).subscribe({
      next: res => {
        this.isLoading = false;
        this.getAllOpenExchanges();
        this.toast.showToast('¡Bien!', 'Juegos intercambiados', 'success');
        this.fetchOpenerEmailAndOpenEmailClient(id);
      },
      error: res => {
        this.isLoading = false;
        this.toast.showToast('Error', 'Error intercambiando', 'error');
      }
    });
  }

  fetchOpenerEmailAndOpenEmailClient(id: number) {
    const exchange = this.exchanges.find(e => e.id === id);
    if (exchange) {
      this.userService.getUserByUsername(exchange.exchangeOpener).subscribe({
        next: (user: User) => {
          const recipientEmail = user.email;
          if (recipientEmail) {
            const subject = 'Intercambio de Juegos';
            const body = `Hola,\n\nEstoy interesado en intercambiar el juego "${exchange.openerOwnedGameTitle}" por "${exchange.openerExchangeGameTitle}".\n\nSaludos,\n${this.currentUser}`;
            const mailtoLink = `mailto:${recipientEmail}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;
            window.open(mailtoLink, '_blank');
          }
        },
        error: (err: HttpErrorResponse) => {
          this.toast.showToast('Error', 'No se pudo obtener el correo electrónico del usuario', 'error');
        }
      });
    }
  }
}
// import { HttpErrorResponse } from '@angular/common/http';
// import { Component, OnInit } from '@angular/core';
// import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
// import { ExchangeRequest } from 'src/app/models/ExchangeRequest.model';
// import { Game } from 'src/app/models/game.model';
// import { AuthService } from 'src/app/services/auth.service';
// import { ExchangeService } from 'src/app/services/exchange.service';
// import { GameService } from 'src/app/services/game.service';
// import { ToastService } from 'src/app/services/toast.service';
// import { UserService } from 'src/app/services/user.service';
// import { ToastParams } from 'src/app/toast/Toast.model';

// @Component({
//   selector: 'app-exchange',
//   templateUrl: './exchange.component.html',
//   styleUrls: ['./exchange.component.css']
// })
// export class ExchangeComponent implements OnInit {

//   constructor(private exchangeService: ExchangeService,
//     private auth: AuthService,
//     private userService: UserService,
//     private gameService: GameService,
//     private toast: ToastService) { }

//   exchanges: ExchangeRequest[] = [];
//   currentUser: string = this.auth.getCurrentLoggedInUsername();
//   isCreatingNewExchange: boolean = false;
//   userGames: Game[] = [];
//   allGames: Game[] = [];
//   exchangeForm: FormGroup = new FormGroup({})
//   isError: boolean = false;
//   isLoading: boolean = false;

//   ngOnInit(): void {
//     this.getAllOpenExchanges();
//     this.getAllUserGames();
//     this.getAllGames();

//     this.exchangeForm = new FormGroup({
//       openerOwnedGameId: new FormControl(null, Validators.required),
//       openerExchangeGameId: new FormControl(null, Validators.required)
//     })
//   }

//   getAllOpenExchanges(){
//     this.isLoading = true;
//     this.exchangeService.getAllOpenExchanges().subscribe({
//       next: (res: ExchangeRequest[]) => {
//         this.isLoading = false;
//         this.exchanges = res;
//       }
//     });
//   }

//   getAllUserGames(){
//     this.userService.getUsersGames(this.auth.getCurrentLoggedInUsername()).subscribe({
//       next: res => {
//         this.userGames = res;
//       }
//     });
//   }

//   getAllGames(){
//     this.gameService.getAllGames().subscribe({
//       next: res => {
//         this.allGames = res;
//       }
//     });
//   }

//   onCloseModal(){
//     this.isCreatingNewExchange = false;
//     this.exchangeForm.reset();
//   }

//   onSubmit(){
//     this.isLoading = true;
//     this.exchangeService.createExchange(this.exchangeForm.value).subscribe({
//       next: res => {
//         this.isLoading = false;
//         this.onCloseModal();
//         this.toast.showToast('Exchange created', 'Your exchange has been created', 'success');
//         this.getAllOpenExchanges();
//       },
//       error: (res:HttpErrorResponse) => {
//         this.isLoading = false;
//         this.toast.showToast('Error', 'Could not create exchange', 'error');
//       }
//     });
//   }

//   onCancelExchange(id: number){
//     this.isLoading = true;
//     this.exchangeService.cancelExchange(id).subscribe({
//       next: res => {
//         this.isLoading = false;
//         this.getAllOpenExchanges();
//         this.toast.showToast('Success', 'Exchange request cancelled', 'success');
//       },
//       error: res => {
//         this.isLoading = false;
//         this.toast.showToast('Error', 'Could not cancel', 'error');
//       }
//     });
//   }

//   processExchange(id: number){
//     this.isLoading = true;
//     this.exchangeService.processExchange(id).subscribe({
//       next: res => {
//         this.isLoading = false;
//         this.getAllOpenExchanges();
//         this.toast.showToast('¡Bien!', 'Juegos intercambiados', 'success');
//       },
//       error: res => {
//         this.isLoading = false;
//         this.toast.showToast('Error', 'Error intercambiando', 'error');
//       }
//     });
//   }

// }
