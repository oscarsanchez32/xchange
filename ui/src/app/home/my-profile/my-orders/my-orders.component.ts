import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  orders: Order[] = [];
  isLoading: boolean = true;

  constructor(private userService: UserService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.userService.getUsersOrders(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: (res: Order[]) => {
        this.orders = res;
        this.isLoading = false;
      },
      error: error => {
        this.isLoading = false;
        console.error(error.message);
      }
    });
  }

}
