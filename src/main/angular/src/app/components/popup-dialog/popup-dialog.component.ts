import {Component, OnInit, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-popup-dialog',
  templateUrl: './popup-dialog.component.html',
  styleUrls: ['./popup-dialog.component.scss']
})
export class PopupDialogComponent implements OnInit {

  success: boolean = true;
  message: string = "Operazione eseguita con successo!";

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: { 
      response?: any;
    }
  ) {
    if (this.data.response?.esito) {
      this.success = this.data.response.esito === 'OK';
    }

    if (this.data.response?.message) {
      this.message = this.data.response.message;
    }
  }

  ngOnInit() { }

}
