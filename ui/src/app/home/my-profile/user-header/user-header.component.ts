import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css']
})
export class UserHeaderComponent implements OnInit {

  userCreditBal: number = 0;
  currentUser: User = new User();   // any user
  currentLoggedInUser: string = ''; // current logged in user
  viewingOtherProfile: boolean = false;
  isLoadingHeader: boolean = false;

  constructor(private userService: UserService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.currentLoggedInUser = this.authService.getCurrentLoggedInUsername();
    
    this.getUserDetails();

    this.userService.userUpdated.subscribe(res => {
      this.getUserDetails();
    });
    
    this.getUserCreditBalance();
  }
  
  getUserDetails(){
    this.isLoadingHeader = true;
    this.route.params.subscribe((param: Params)=>{
      this.userService.getUserByUsername(param['user']).subscribe({
        next: res => {
          this.currentUser = res;
          this.viewingOtherProfile = this.currentUser.username != this.currentLoggedInUser;
          this.isLoadingHeader = false;
        },
        error: res => {
          this.router.navigate(['/not-found']);
          this.isLoadingHeader = false;
        }
      })
    });
  }
  
  getUserCreditBalance(){
    this.userService.getUserCredit(this.authService.getCurrentLoggedInUsername()).subscribe({
      next: res => {
        this.userCreditBal = res.amount;
      },
      error: error => {
        this.userCreditBal = -1;
      }
    });
  }

}
