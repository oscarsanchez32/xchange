import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user.model';
import jwtDecode from 'jwt-decode';
import * as moment from 'moment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
    private router: Router) { }

  currentUser: User = new User();

  userHasLoggedOut = new BehaviorSubject<boolean>(false);

  login(usernamePassword: object): Observable<any>{
    return this.http.post<any>(`${environment.baseUrl}/auth/login`, usernamePassword);
  }

 

  register(newAccountReq: object): Observable<any>{
    return this.http.post<any>(`${environment.baseUrl}/auth/register`, newAccountReq);
  }

  fetchCurrentUserDetails(): Observable<User>{
    return this.http.get<User>(`${environment.baseUrl}/user/me`);
  }

  isLoggedIn(): boolean{
    if(localStorage.getItem('token') === null) return false;
    return !this.isTokenExpired();
  }

  setLoggedInUser(){
    if(this.isLoggedIn()){
      this.fetchCurrentUserDetails().subscribe(res=>{
        this.currentUser = res;
      })
    }
  }

  getExpiration(){
    try {
      const bearerToken = localStorage.getItem('token');
      const decodedJWT: {iat: number, sub: string, exp: number } = jwtDecode(bearerToken!);
      return moment.unix(decodedJWT.exp);
    } catch(error) {
      console.log('Error decoding token.');
      return;
    }
  }

  getCurrentLoggedInUsername(): string{
    try{
      const bearerToken = localStorage.getItem('token');
      const decodedJWT: {iat: number, sub: string, exp: number} = jwtDecode(bearerToken!);
      return decodedJWT.sub;
    } catch(error){
      console.log('Error decoding token');
      return '';
    }
  }

  getCurrentUser(): User{ 
    if(localStorage.getItem('currentUser') !== null){
      this.currentUser = JSON.parse(localStorage.getItem('currentUser')!);
      return this.currentUser;
    } else {
      console.error('Error getting current user.');
      return this.currentUser;
    }
  }
  
  isTokenExpired(){
    const bearerToken = localStorage.getItem('token');
    if(bearerToken === null){
      return true;
    }

    const decodedJWT: {iat: number, sub: string, exp: number } = jwtDecode(bearerToken);
    return moment().isAfter(moment.unix(decodedJWT.exp)); 
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
  }

  getAuthHeader() {
    const bearerToken = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${bearerToken}`
    });
  }

}
