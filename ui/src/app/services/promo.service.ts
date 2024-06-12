import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Promo } from '../models/promo.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PromoService {

  constructor(private http: HttpClient) { }

  getPromo(code: string): Observable<Promo>{
    return this.http.get<Promo>(`${environment.baseUrl}/promo/${code}`);
  }
}
