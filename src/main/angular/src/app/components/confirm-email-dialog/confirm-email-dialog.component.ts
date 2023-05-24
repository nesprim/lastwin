import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmEmailModel } from 'src/app/model/confirm-email.model';
import { AuthService } from 'src/app/services/auth.service';
import { PopupDialogService } from 'src/app/services/popup-dialog.service';

@Component({
  selector: 'app-confirm-email-dialog',
  templateUrl: './confirm-email-dialog.component.html',
  styleUrls: ['./confirm-email-dialog.component.scss']
})
export class ConfirmEmailDialogComponent implements OnInit {

  loading: boolean = false;
  confirmEmailModel: ConfirmEmailModel = new ConfirmEmailModel;

  constructor(
    private authService: AuthService,
    private popupDialogService: PopupDialogService,
    private dialogRef: MatDialogRef<ConfirmEmailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: { 
        username?: any;
      }
  ) {
    this.confirmEmailModel.username = this.data.username;
  }

  ngOnInit() { }

  confirmEmail() {
    this.loading = true;

    this.authService.confirmEmail(this.confirmEmailModel).subscribe(response => {
        if (response.esito === 'OK') {
            this.dialogRef.close();
        }

        this.loading = false;
        this.popupDialogService.openPopupDialog('350px', response);
    })
  }

}
