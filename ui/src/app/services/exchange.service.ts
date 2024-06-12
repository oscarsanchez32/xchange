import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ExchangeRequest } from '../models/ExchangeRequest.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {

  constructor(private http: HttpClient,
    private auth: AuthService) { }

  getAllOpenExchanges(): Observable<ExchangeRequest[]>{
    return this.http.get<ExchangeRequest[]>(`${environment.baseUrl}/exchange`);
  }

  createExchange(exchangeCreate: Object): Observable<any>{
    return this.http.post(`${environment.baseUrl}/exchange`, exchangeCreate);
  }

  cancelExchange(id: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}/exchange/${id}/cancel`);
  }

  processExchange(id: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}/exchange/${id}/process`);
  }

}
