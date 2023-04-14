import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { LoginModel } from 'src/app/model/login.model';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.scss']
})

export class AuthComponent implements OnInit {
    loading: boolean = false;
    loginModel: LoginModel = new LoginModel;

    constructor(
        private authService: AuthService
    ) {}

    ngOnInit(): void { 
        //leggere token jwt
    }

    signin() {
        this.loading = true;

        this.authService.signin(this.loginModel).subscribe(response => {
            if (response.esito === 'OK') {
                localStorage.setItem('token_jwt_lastwin', response.data.token);
            } else {
                //mostrare popup
            }
            
            this.loading = false;
        })
    }
}