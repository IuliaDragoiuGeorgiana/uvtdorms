import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RegisterDialogData } from '../../../../interfaces/register-dialog-data';

@Component({
  selector: 'app-register-error-dialog',
  templateUrl: './register-error-dialog.component.html',
  styleUrl: './register-error-dialog.component.css',
})
export class RegisterErrorDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RegisterErrorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RegisterDialogData
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
