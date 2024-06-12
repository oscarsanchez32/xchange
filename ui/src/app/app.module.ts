import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ErrorComponent } from './error/error.component';
import { FooterComponent } from './footer/footer.component';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';
import { HomeComponent } from './home/home.component';
import { ModalComponent } from './modal/modal.component';
import { ToastComponent } from './toast/toast.component';
import { CartComponent } from './home/cart/cart.component';
import { CheckoutComponent } from './home/checkout/checkout.component';
import { MyProfileComponent } from './home/my-profile/my-profile.component';
import { ProfileNavigationComponent } from './home/my-profile/profile-navigation/profile-navigation.component';
import { MyReviewsComponent } from './home/my-profile/my-reviews/my-reviews.component';
import { UserHeaderComponent } from './home/my-profile/user-header/user-header.component';
import { MyOrdersComponent } from './home/my-profile/my-orders/my-orders.component';
import { PaymentSuccessComponent } from './home/payment-success/payment-success.component';
import { AuthIntercepterService } from './services/auth-interceptor.service';
import { EditProfileComponent } from './home/my-profile/edit-profile/edit-profile.component';
import { ExchangeComponent } from './home/exchange/exchange.component';
import { ExchangesComponent } from './home/my-profile/exchanges/exchanges.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ToastrModule } from 'ngx-toastr';
import { AboutComponent } from './home/about/about.component';
import { OwnedGamesComponent } from './home/my-profile/owned-books/owned-games.component';
import { GameListComponent } from './home/games/game-list/game-list.component';
import { GameDetailComponent } from './home/games/game-detail/game-detail.component';
import { GamesComponent } from './home/games/games.component';
import { ReviewsComponent } from './home/games/game-detail/reviews/reviews.component';
import { GameFormComponent } from './home/games/game-form/game-form.component';
import { PaypalButtonComponent } from './paypal-button/paypal-button.component';
import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';

registerLocaleData(localeEs, 'es-ES');
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    GameListComponent,
    GameDetailComponent,
    ErrorComponent,
    FooterComponent,
    GamesComponent,
    ReviewsComponent,
    AuthComponent,
    LoginComponent,
    RegisterComponent,
    LoaderComponent,
    GameFormComponent,
    HomeComponent,
    ModalComponent,
    ToastComponent,
    CartComponent,
    CheckoutComponent,
    MyProfileComponent,
    ProfileNavigationComponent,
    MyReviewsComponent,
    UserHeaderComponent,
    MyOrdersComponent,
    OwnedGamesComponent,
    PaymentSuccessComponent,
    EditProfileComponent,
    ExchangeComponent,
    ExchangesComponent,
    NotFoundComponent,
    AboutComponent,
    PaypalButtonComponent,
    // EventDetailComponent,
    // EventFormComponent,
    // EventListComponent,
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthIntercepterService, multi: true},  { provide: LOCALE_ID, useValue: 'es-ES' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
