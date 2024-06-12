import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Game } from 'src/app/models/game.model';
import { GameService } from 'src/app/services/game.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-game-form',
  templateUrl: './game-form.component.html',
  styleUrls: ['./game-form.component.css']
})
export class GameFormComponent implements OnInit {

  gameForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  isSaving: boolean = false;
  editGameId: number = 0;
  
  constructor(private gameService: GameService,
    private currentRoute: ActivatedRoute,
    private toast: ToastService) { }
  
  @Input()
  editMode: boolean = false;

  @Input()
  game!: Game;

  @Output()
  modalClose = new EventEmitter<void>();

  ngOnInit(): void {
    if(this.editMode){
      this.gameForm = new FormGroup({
        title: new FormControl(this.game.title, Validators.required),
        developera: new FormControl(this.game.developera, Validators.required),
        price: new FormControl(this.game.price, Validators.required),
        shortDesc: new FormControl(this.game.shortDesc, Validators.required),
        imgPath: new FormControl(this.game.imgPath, Validators.required),
        tags: new FormControl(this.game.tags),
        gameDetail: new FormGroup({
          ean: new FormControl(this.game.gameDetail.ean, Validators.required),
          language: new FormControl(this.game.gameDetail.language, Validators.required),
          publisher: new FormControl(this.game.gameDetail.publisher, Validators.required),
          longDesc: new FormControl(this.game.gameDetail.longDesc)
        })
      })
    } else {
      this.gameForm = new FormGroup({
        title: new FormControl('', Validators.required),
        developera: new FormControl('', Validators.required),
        price: new FormControl('', Validators.required),
        shortDesc: new FormControl('', [Validators.required, Validators.maxLength(255)]),
        imgPath: new FormControl('', Validators.required),
        tags: new FormControl(''),
        gameDetail: new FormGroup({
          ean: new FormControl('', Validators.required),
          language: new FormControl('', Validators.required),
          publisher: new FormControl('', Validators.required),
          longDesc: new FormControl('', Validators.maxLength(2048))
        })
      });
    }
  }

  onCloseModal(){
    this.modalClose.emit();
  }

  onSubmit(){
    this.isSaving = true;
    if(!this.editMode){
      this.gameService.saveGame(this.gameForm.value).subscribe({
        next: res => {
          this.isSaving = false;
          this.onCloseModal();
          this.toast.showToast('Juego subido', '', 'exótico');
          //location.reload();
        },
        error: error => {
          console.log(error.message);
          this.toast.showToast('Error', 'Error subiendo el juego', 'error');
        }
      })
    } else {
      this.currentRoute.params.subscribe((params)=>{
        this.editGameId = +params['id'];
      })
      
      let gameToSave: Game = this.gameForm.value;
      gameToSave.id = this.editGameId;
      
      this.gameService.updateGame(gameToSave).subscribe({
        next: res => {
          this.isSaving = false;
          this.onCloseModal();
          this.toast.showToast('Juego guardado', '', '¡Bien!');
        },
        error: error => {
          this.isSaving = false;
          console.log(error.message);
          this.toast.showToast('Error', 'Error guardando el juego', 'error');
        }
      })
    }
  }

  fetchRandomGame(){
    this.isLoading = true;
    this.gameService.getRandomGame().subscribe({
      next: res => {
        this.isLoading = false;
        this.gameForm.setValue({
          title: res.title,
          developera: res.developera,
          price: res.price,
          shortDesc: res.shortDesc,
          imgPath: res.imgPath,
          gameDetail: res.gameDetail,
          tags: res.tags
        })
      },
      error: error => {
        this.isLoading = false;
        console.log(error.message);
      }
    })
  }

  onReset(){
    this.gameForm.reset();
  }

}
