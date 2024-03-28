import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-register-confirm-dialog',
  templateUrl: './register-confirm-dialog.component.html',
  styleUrl: './register-confirm-dialog.component.css',
})
export class RegisterConfirmDialogComponent {
  constructor(public dialogRef: MatDialogRef<RegisterConfirmDialogComponent>) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
