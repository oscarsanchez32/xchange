import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  template: `
  <div class="text-center d-flex justify-content-center pt-5">
    <div class="alert alert-danger col-md-4" role="alert">
      <i class="bi bi-info-circle me-2"></i> <span>{{message === '' || message === null ? 'Error fetching content.' : message}}</span>
    </div>
  </div>
  `,
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  constructor() { }

  @Input()
  message: string = '';

  ngOnInit(): void {
  }

}
