import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  isError: boolean = false;
  invalidCredentials: boolean = false;
  userLoggedOut: boolean = false;
  userLogoutSubscription!: Subscription;


  constructor(private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private toast: ToastService) { }

  ngOnInit(): void {
    this.userLogoutSubscription = this.authService.userHasLoggedOut.subscribe({
      next: loggedOut => {
        this.userLoggedOut = loggedOut;
        setTimeout(()=>{
          if(this.userLoggedOut) this.userLoggedOut = false;
        }, 3000);
      }
    });

    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnDestroy(): void {
    this.userLogoutSubscription.unsubscribe();
  }

  onSubmitLogin(){
    this.isLoading = true;
    const formValue = this.loginForm.value;

    this.authService
    .login({
      username: formValue.username,
      password: formValue.password,
    })
    .subscribe({
      next: res => {
        this.isLoading = false;
        localStorage.setItem('token', res.token);

        this.authService.fetchCurrentUserDetails().subscribe({
          next: res => {
            localStorage.setItem('currentUser', JSON.stringify(res));
            this.router.navigate(['/games/list']);
          }
        })

      },
      error: res => {
        if(res.message.indexOf("403") != -1){
          this.isLoading = false;
          this.invalidCredentials = true;
          setTimeout(()=>{this.invalidCredentials = false}, 3000)
        } else {
          this.isLoading = false;
          this.isError = true;
          setTimeout(() => {
            this.isError = false;
          }, 5000);
          console.error(res.message);
        }
      }
    });
  }

}
