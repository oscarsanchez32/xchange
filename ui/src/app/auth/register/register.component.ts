import { Expression } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private userService: UserService,
    private auth: AuthService,
    private router: Router,
    private toast: ToastService) { }

  registerForm!: FormGroup;
  isLoadingRandomData: boolean = false;

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.email, Validators.required])
    });
  }

  onRegisterSubmit(){
    let username = this.registerForm.get('username');
    let password = this.registerForm.get('password');
    
    console.log(this.registerForm.value)
    this.auth.register(this.registerForm.value).subscribe({
      next: res => {
        this.router.navigate(['/login']);
        this.toast.showToast('¡Cuenta creada!', '', 'success');
      },
      error: res => {
        if(res.error.message.indexOf('That username has been taken') !== -1){
          this.toast.showToast('Este usuario ya existe', '', 'error');
        } else if(res.error.message.indexOf('email') !== -1){
          this.toast.showToast('Este email ya existe', '', 'error')
        }
      }
    });
  }

  generateUser(){
    this.isLoadingRandomData = true;
    this.userService.getRandomUser().subscribe({
      next: res => {
        this.isLoadingRandomData = false;
        this.registerForm.setValue({
          username: res.username,
          firstName: res.firstName,
          lastName: res.lastName,
          email: res.email,
          password: 'pass'
        });
      }
    });
  }

}
