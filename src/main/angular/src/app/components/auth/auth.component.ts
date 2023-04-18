import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { LoginModel } from 'src/app/model/login.model';
import { PopupDialogService } from 'src/app/services/popup-dialog.service';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.scss']
})

export class AuthComponent implements OnInit {
    loading: boolean = false;
    loginModel: LoginModel = new LoginModel;

    constructor(
        private authService: AuthService,
        private popupDialogService: PopupDialogService
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
                this.popupDialogService.openPopupDialog('350px', response);
            }
            
            this.loading = false;
        })
    }

    showSignupPopup() {
        this.popupDialogService.openSignupDialog();
    }
}