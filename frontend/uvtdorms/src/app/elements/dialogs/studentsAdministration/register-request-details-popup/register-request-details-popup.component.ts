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
  public isLoadingScreenVisible: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<RegisterRequestDetailsPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RegisterRequestDto,
    private registerRequestService: RegisterRequestService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  decline(): void {
    this.isLoadingScreenVisible = true;
    this.registerRequestService.declineRegisterRequest(this.data).subscribe({
      error: (error) => {
        console.error(error);
      },
      next: () => {
        window.location.reload();
      },
      complete: () => {
        this.isLoadingScreenVisible = false;
      },
    });
  }

  accept(): void {
    this.isLoadingScreenVisible = true;
    this.registerRequestService.acceptRegisterRequest(this.data).subscribe({
      error: (error) => {
        console.error(error);
      },
      next: () => {
        window.location.reload();
      },
      complete: () => {
        this.isLoadingScreenVisible = false;
      },
    });
  }
}
