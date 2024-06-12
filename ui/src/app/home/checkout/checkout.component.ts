import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PaymentType } from 'src/app/models/PaymentType';
import { Game } from 'src/app/models/game.model';
import { Promo } from 'src/app/models/promo.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { GameService } from 'src/app/services/game.service';
import { OrderService } from 'src/app/services/order.service';
import { PaymentService } from 'src/app/services/payment.service';
import { PromoService } from 'src/app/services/promo.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';
//import { GooglePay, PaymentDataRequest } from '@types/googlepay';



@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})


export class CheckoutComponent implements OnInit {

  cart: Game[] = [];
  constructor(private cartService: CartService,
    private promoService: PromoService,
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private paymentService: PaymentService,
    private currentRoute: ActivatedRoute,
    private gameService: GameService,
    private toast: ToastService) { }

    
  total: number = 0;
  promoApplied: boolean = false;
  invalidPromo: boolean = false;
  promo: Promo = {
    id: 0,
    code: '',
    amount: 0
  };
  userCredit: number = 0;
  loadingCardCheckout: boolean = false;
  isLoadingPromo: boolean = false;
  isLoadingYourOrder: boolean = false;

  cancelledCardPayment: boolean = false;
  cancelledOrderId: number = -1;

  form: FormGroup = new FormGroup({
    paymentMode: new FormControl('card', Validators.required)
  })
  
  ngOnInit(): void {
    if(this.currentRoute.snapshot.queryParams['type'] === 'instant-checkout'){
      let instantCheckoutGameId: number = this.currentRoute.snapshot.queryParams['id'];
      this.gameService.getGameById(instantCheckoutGameId).subscribe({
        next: res => {
          this.cart = [];
          this.cart.push(res);
          this.calculateTotal(this.cart);
            //paypal

        }
      });
    } else {
      this.getUserCart();
    }

    this.getUserCredit(this.authService.getCurrentLoggedInUsername());

    this.cancelledCardPayment = this.currentRoute.snapshot.queryParams['cancelled'];
    this.cancelledOrderId = this.currentRoute.snapshot.queryParams['orderId'];

    if(this.cancelledCardPayment && this.cancelledOrderId) {}

    setTimeout(() => {
      if(this.cancelledCardPayment) this.cancelledCardPayment = false;
    }, 5000);

    
  }
  
  getUserCart(){
    this.isLoadingYourOrder = true;
    this.cartService.getUserCart().subscribe({
      next: res => {
        this.cart = res;
        this.calculateTotal(this.cart);
        this.isLoadingYourOrder = false;
      }
    });
  }

  calculateTotal(cart: Game[]){
    this.total = 0;
    for(let item of cart){
      this.total += item.price;
    }
  }

  onApplyPromo(form: NgForm){
    if(this.promo.code === form.value.promo) return;
    // reset to original price every time user enters new code
    this.onRemoveDiscount();

    this.isLoadingPromo = true;
    this.promoService.getPromo(form.value.promo).subscribe({
      next: res => {
        this.isLoadingPromo = false;
        this.invalidPromo = false;
        this.promoApplied = true;
        this.promo = res;
        this.total -= this.promo.amount;
        if(this.total < 0) this.total = 0;
        this.toast.showToast('Promo applied', this.promo.code+': '+this.promo.amount+'$ off', 'success');
      },
      error: res => {
        this.isLoadingPromo = false;
        console.error(res);
        this.invalidPromo = true;
      }
    });
  }

  getUserCredit(username: string){
    this.userService.getUserCredit(username).subscribe({
      next: res => {
        this.userCredit = res.amount;
      },
      error: res => {
        console.error(res);
      }
    });
  }

  onRemoveDiscount(){
    this.promo = {id: 0, amount: 0, code: ''}
    this.promoApplied = false;
    this.calculateTotal(this.cart);
  }

  onSubmitPaymentMode(){
    let orderItems: number[] = [];
    this.cart.forEach(game => {
      orderItems.push(game.id);
    });
    switch (this.form.get('paymentMode')?.value) {
      case 'store-credit':
        if(this.total > this.userCredit) {
          this.toast.showToast('Insufficient credits', 'You do not have enough credits', 'info');
          return;
        }
        this.paymentService.processPayment(orderItems, this.promoApplied ? this.promo.code : "", PaymentType.COINS)
          .subscribe({
            next: res => {
              this.router.navigate(['/games/list'], {queryParams: {orderSuccess: ''}});
              this.toast.showToast('Order created', 'Item(s) has been added', 'success');
            },
            error: res => {
              console.error(res);
              this.router.navigate(['/games/list'], {queryParams: {orderFailure: ''}});
              this.toast.showToast('Error', 'Could not create order', 'error');
            }
          });
        break;
      
      case 'card':
        this.loadingCardCheckout = true;
        this.paymentService.processPayment(orderItems, this.promoApplied ? this.promo.code : "", PaymentType.TARJETA).subscribe({
          next: res => {
            this.loadingCardCheckout = false;
            window.location.href = res.session_url;
          },
          error: error => {
            console.log(error);
          }
        });
          
        break;
    
      default:
        break;
    }
  }
  ngAfterViewInit(): void {
    this.initGooglePay();
  }

  initGooglePay(): void {
    const paymentsClient = new google.payments.api.PaymentsClient({ environment: 'TEST' });
    const button = paymentsClient.createButton({
      onClick: this.onGooglePay.bind(this)
    });
    document.getElementById('google-pay-button')?.appendChild(button);
  }

  onGooglePay(): void {
    const paymentsClient = new google.payments.api.PaymentsClient({ environment: 'TEST' });
    const paymentDataRequest = this.getGooglePaymentDataRequest();
    paymentsClient.loadPaymentData(paymentDataRequest)
      .then((paymentData: google.payments.api.PaymentData) => {
        // Procesa el pago con la información recibida
        this.processGooglePay(paymentData);
      })
      .catch((error: any) => {
        console.error('Google Pay error:', error);
      });
  }

  getGooglePaymentDataRequest(): google.payments.api.PaymentDataRequest {
    return {
      apiVersion: 2,
      apiVersionMinor: 0,
      allowedPaymentMethods: [{
        type: 'CARD',
        parameters: {
          allowedAuthMethods: ['PAN_ONLY', 'CRYPTOGRAM_3DS'],
          allowedCardNetworks: ['MASTERCARD', 'VISA']
        },
        tokenizationSpecification: {
          type: 'PAYMENT_GATEWAY',
          parameters: {
            gateway: 'example',
            gatewayMerchantId: 'exampleGatewayMerchantId'
          }
        }
      }],
      merchantInfo: {
        merchantId: 'BCR2DN6TRPGL5MYT',
        merchantName: 'Example Merchant'
      },
      transactionInfo: {
        totalPriceStatus: 'FINAL',
        totalPriceLabel: 'Total',
        totalPrice: this.total.toFixed(2),
        currencyCode: 'EUR',
        countryCode: 'ES'
      }
    };
  }

  processGooglePay(paymentData: google.payments.api.PaymentData): void {
    // Procesa la información del pago recibida desde Google Pay
    console.log('Google Pay payment data:', paymentData);
    // Lógica para procesar el pago en el servidor
  }


}
