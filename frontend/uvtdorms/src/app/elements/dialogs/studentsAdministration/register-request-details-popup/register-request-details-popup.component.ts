import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RegisterRequestDto } from '../../../../interfaces/register-request-dto';

@Component({
  selector: 'app-register-request-details-popup',
  templateUrl: './register-request-details-popup.component.html',
  styleUrl: './register-request-details-popup.component.css'
})
export class RegisterRequestDetailsPopupComponent {
  constructor(
    public dialogRef: MatDialogRef<RegisterRequestDetailsPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RegisterRequestDto
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
