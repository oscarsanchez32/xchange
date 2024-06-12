import { Component, OnInit } from '@angular/core';
import { Game } from 'src/app/models/game.model';
import { CartService } from 'src/app/services/cart.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  constructor(private cartService: CartService,
    private toast: ToastService) { }

  cart: Game[] = [];
  total: number = 0;
  isLoading: boolean = false;

  ngOnInit(): void {
    this.isLoading = true;
    this.cartService.getUserCart().subscribe({
      next: res => {
        this.isLoading = false;
        this.cart = res;
        this.calculateTotal(this.cart);
      }
    });
  }

  onRemoveFromCart(id: number) {
    this.cartService.removeFromCart(id).subscribe({
      next: res => {
        this.ngOnInit();
        this.toast.showToast('Item removed', 'Item removed from cart', 'success');
        this.cartService.cartUpdated.next(true);
      },
      error: res => {
        this.toast.showToast('Error', 'Could not remove item from cart', 'error');
      }
    });
  }

  calculateTotal(cart: Game[]){
    this.total = 0;
    for(let item of cart){
      this.total += item.price;
    }
  }

}
