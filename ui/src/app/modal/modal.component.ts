import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  constructor() { }

  @Output()
  onClickBackdrop = new EventEmitter<void>();

  ngOnInit(): void {
  }

  onCloseModal(){
    this.onClickBackdrop.emit();
  }

}
