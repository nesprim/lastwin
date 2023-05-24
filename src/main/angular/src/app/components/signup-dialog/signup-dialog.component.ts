import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { RegisterModel } from 'src/app/model/register.model';
import { PopupDialogService } from 'src/app/services/popup-dialog.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-signup-dialog',
  templateUrl: './signup-dialog.component.html',
  styleUrls: ['./signup-dialog.component.scss']
})
export class SignupDialogComponent implements OnInit {
  
  loading: boolean = false;
  registerModel: RegisterModel = new RegisterModel;

  constructor(
    private authService: AuthService,
    private popupDialogService: PopupDialogService,
    private dialogRef: MatDialogRef<SignupDialogComponent>
  ) {}

  ngOnInit() { }

  signup() {
    this.loading = true;

    this.authService.signup(this.registerModel).subscribe(response => {
      if (response.esito === 'OK') {
        this.dialogRef.close();
      } 
        
      this.loading = false;
      this.popupDialogService.openPopupDialog('350px', response);
    })
  }

}