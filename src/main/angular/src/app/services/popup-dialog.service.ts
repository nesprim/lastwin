import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupDialogComponent } from '../components/popup-dialog/popup-dialog.component';
import { SignupDialogComponent } from '../components/signup-dialog/signup-dialog.component';
import { Observable } from 'rxjs';
import { ConfirmEmailDialogComponent } from '../components/confirm-email-dialog/confirm-email-dialog.component';

@Injectable({
    providedIn: 'root'
})

export class PopupDialogService {

    constructor(
        private dialog: MatDialog
    ) {}

    openPopupDialog(widthInput: any, response: any): Observable<boolean> {
        return this.dialog.open(PopupDialogComponent, {
            width: widthInput,
            data: { response }
        }).afterClosed();
    }

    openSignupDialog(): Observable<boolean> {
        return this.dialog.open(SignupDialogComponent, {
            width: '700px'
        }).afterClosed();
    }

    openConfirmDialog(username: any): Observable<boolean> {
        return this.dialog.open(ConfirmEmailDialogComponent, {
            width: '550px',
            data: { username }
        }).afterClosed();
    }
}