import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loggedIn: boolean = false;
  isAdmin: boolean = false;
  currentUser: User = new User();
  cartItems: number = 0;

  constructor(public authService: AuthService, private router: Router,
    private userService: UserService, private cartService: CartService) { }

  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
    if(this.loggedIn) {
      this.currentUser = this.authService.getCurrentUser();
      this.getCartCount();
    }

    // update cart count every time cart is updated
    this.cartService.cartUpdated.subscribe({
      next: res => {
        this.getCartCount();
      }
    })
  }
  
  onLogout(): void {
    this.authService.logout();
    this.authService.userHasLoggedOut.next(true);
    this.router.navigate(['/login']);
  }

  getCartCount(){
    this.userService.getUserCartCount(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.cartItems = res.cart_items;
      }
    });
  }

}
