import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModifiableLaundryAppointmentService } from '../../services/modifiable-laundry-appointment.service';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-keep-laundry-appointment-page',
  templateUrl: './keep-laundry-appointment-page.component.html',
  styleUrl: './keep-laundry-appointment-page.component.css',
})
export class KeepLaundryAppointmentPageComponent {
  private id: string;
  private isValidationLoading: boolean = false;
  private isCancelLoading: boolean = false;
  private isKeepLoading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private modifiableLaundryAppointmentService: ModifiableLaundryAppointmentService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {
    this.id = this.route.snapshot.paramMap.get('id')!;
    console.log('ID: ', this.id);
  }

  ngOnInit() {
    this.isValidationLoading = true;
    this.modifiableLaundryAppointmentService
      .validateModifiableLaundryAppointment(this.id)
      .subscribe({
        next: (response) => {
          console.log('Response: ', response);
        },
        error: (error) => {
          console.log('Error: ', error);
          this.router.navigate(['/profile']);
        },
        complete: () => {
          this.isValidationLoading = false;
        },
      });
  }

  get isLoadingScreenVisible(): boolean {
    return (
      this.isValidationLoading || this.isCancelLoading || this.isKeepLoading
    );
  }

  public confirmCancel(): void {
    console.log('Confirm cancel');
    this.confirmationService.confirm({
      message: 'Are you sure you want to cancel this laundry appointment?',
      header: 'Please confirm',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this.isCancelLoading = true;
        this.modifiableLaundryAppointmentService
          .cancelLaundryAppointment({ id: this.id })
          .subscribe({
            next: () => {
              this.router.navigate(['/profile']);
            },
            error: (error) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Something went wrong',
                detail: 'Please try again later.',
                life: 3000,
              });
            },
            complete: () => {
              this.isCancelLoading = false;
            },
          });
      },
      reject: () => {
        console.log('User rejected');
      },
    });
  }

  public confirmKeep(): void {
    console.log('Confirm keep');
    this.confirmationService.confirm({
      message: 'Are you sure you want to keep this laundry appointment?',
      header: 'Please confirm',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this.isKeepLoading = true;
        this.modifiableLaundryAppointmentService
          .keepLaundryAppointmentWithoutDryer({ id: this.id })
          .subscribe({
            next: () => {
              this.router.navigate(['/profile']);
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Something went wrong',
                detail: 'Please try again later.',
                life: 3000,
              });
            },
            complete: () => {
              this.isKeepLoading = false;
            },
          });
      },
      reject: () => {
        console.log('User rejected');
      },
    });
  }
}
