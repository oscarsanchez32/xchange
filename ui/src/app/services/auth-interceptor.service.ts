import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthIntercepterService implements HttpInterceptor {
    
    constructor (private auth: AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(req.url.indexOf('/login') !== -1) return next.handle(req);
        
        const bearerToken = localStorage.getItem('token');
        const reqWithToken = req.clone({headers: req.headers.set('Authorization', 'Bearer '+bearerToken!)});
        return next.handle(reqWithToken);
    }
    
}