import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './profile-navigation.component.html',
  styleUrls: ['./profile-navigation.component.css']
})
export class ProfileNavigationComponent implements OnInit {

  currentLoggedInUsername: string = '';
  currentUrlUsername: string = ''; // for viewing other profiles

  constructor(private authService: AuthService,
      private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.currentLoggedInUsername = this.authService.getCurrentLoggedInUsername();
    this.route.params.subscribe(param => {
      this.currentUrlUsername = param['user'];
    })
  }

}
