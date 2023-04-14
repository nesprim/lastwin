import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
    providedIn: 'root'
})

export class AuthService {
    constructor(
        private http: HttpClient
    ) {}

    signin(loginModel: any): Observable<any> {
        return this.http.post<any>(`/auth/signin`, loginModel).pipe(
            map(response => response)
        );
    }
}
