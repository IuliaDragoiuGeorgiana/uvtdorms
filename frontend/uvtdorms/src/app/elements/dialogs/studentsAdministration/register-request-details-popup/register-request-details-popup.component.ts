import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RegisterRequestDto } from '../../../../interfaces/register-request-dto';
import { RegisterRequestService } from '../../../../services/register-request.service';

@Component({
  selector: 'app-register-request-details-popup',
  templateUrl: './register-request-details-popup.component.html',
  styleUrl: './register-request-details-popup.component.css',
})
export class RegisterRequestDetailsPopupComponent {
  constructor(
    public dialogRef: MatDialogRef<RegisterRequestDetailsPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RegisterRequestDto,
    private registerRequestService: RegisterRequestService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  decline(): void {
    this.registerRequestService.declineRegisterRequest(this.data).subscribe({
      error: (error) => {
        console.error(error);
      },
      next: () => {
        console.info('Register request declined!');
      },
    });
  }

  accept(): void {
    this.registerRequestService.acceptRegisterRequest(this.data).subscribe({
      error: (error) => {
        console.error(error);
      },
      next: () => {
        console.info('Register request accepted!');
      },
    });
  }
}
