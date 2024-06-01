import { Component } from '@angular/core';
import { DetailedDormAdministratorDto } from '../../interfaces/detailed-dorm-administrator-dto';
import { AvailableDormDto } from '../../interfaces/available-dorm-dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';
import { DormService } from '../../services/dorm.service';
import {
  ConfirmationService,
  MessageService,
  OverlayListenerOptions,
  OverlayOptions,
} from 'primeng/api';
import { AddNewDormAdministratorDto } from '../../interfaces/add-new-dorm-administrator-dto';
import { UpdateDormAdministratorDto } from '../../interfaces/update-dorm-administrator-dto';
import { EmailDto } from '../../interfaces/email-dto';

@Component({
  selector: 'app-dorm-administrators-page',
  templateUrl: './dorm-administrators-page.component.html',
  styleUrl: './dorm-administrators-page.component.css',
})
export class DormAdministratorsPageComponent {
  public dormAdministrators: DetailedDormAdministratorDto[] = [];
  public defaultAvailableDorm: AvailableDormDto[] = [
    { dormName: 'No dorm', id: '' },
  ];

  public availableDorms: AvailableDormDto[] = [];
  public loading: boolean = true;
  public isAddNewDormAdministratorDialogVisible: boolean = false;

  public editDormAdministratorNewDormId: string = '';

  public addNewDormAdministratorForm: FormGroup = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    phoneNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('^[0-9]*$'),
      Validators.minLength(10),
      Validators.maxLength(10),
    ]),
    dormId: new FormControl(''),
  });

  get hasFirstNameError(): boolean {
    const control = this.addNewDormAdministratorForm.controls['firstName'];
    return control.touched && control.invalid;
  }

  get hasLastNameError(): boolean {
    const control = this.addNewDormAdministratorForm.controls['lastName'];
    return control.touched && control.invalid;
  }

  get hasEmailError(): boolean {
    const control = this.addNewDormAdministratorForm.controls['email'];
    return control.touched && control.invalid;
  }

  get hasPhoneNumberError(): boolean {
    const control = this.addNewDormAdministratorForm.controls['phoneNumber'];
    return control.touched && control.invalid;
  }

  constructor(
    private dormAdministratorDetailsService: DormAdministratorDetailsService,
    private dormService: DormService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.updateDormAdministratorsList();
    this.updateAvailableDormsList();
  }

  private updateAvailableDormsList(): void {
    this.loading = true;
    this.dormService.getAvailableDorms().subscribe({
      next: (dorms: AvailableDormDto[]) => {
        this.availableDorms = dorms;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  private updateDormAdministratorsList(): void {
    this.loading = true;
    this.dormAdministratorDetailsService.getAllDormAdministrators().subscribe({
      next: (administrators: DetailedDormAdministratorDto[]) => {
        this.dormAdministrators = administrators;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  public submitNewDormAdministratorForm(): void {
    this.addNewDormAdministratorForm.markAllAsTouched();
    this.addNewDormAdministratorForm.markAsDirty();
    this.addNewDormAdministratorForm.updateValueAndValidity();
    if (this.addNewDormAdministratorForm.invalid) {
      return;
    }

    let newDormAdministratorDto: AddNewDormAdministratorDto = {
      firstName: this.addNewDormAdministratorForm.controls['firstName'].value,
      lastName: this.addNewDormAdministratorForm.controls['lastName'].value,
      email: this.addNewDormAdministratorForm.controls['email'].value,
      phoneNumber:
        this.addNewDormAdministratorForm.controls['phoneNumber'].value,
      dormId: this.addNewDormAdministratorForm.controls['dormId'].value,
    };

    this.loading = true;
    this.isAddNewDormAdministratorDialogVisible = false;
    this.dormAdministratorDetailsService
      .addNewDormAdministrator(newDormAdministratorDto)
      .subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Dorm administrator added',
          });
          this.isAddNewDormAdministratorDialogVisible = false;
          this.addNewDormAdministratorForm.reset();
          this.updateDormAdministratorsList();
          this.loading = false;
        },
        error: (error) => {
          console.error(error);
          this.isAddNewDormAdministratorDialogVisible = true;
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail:
              'Failed to add dorm administrator: ' + error?.error?.message,
          });
          this.loading = false;
        },
      });
  }

  public getAvailableDormsForEditingAdministrator(
    dormAdministrator: DetailedDormAdministratorDto
  ): any[] {
    if (
      dormAdministrator.dormName === '' ||
      dormAdministrator.dormName === null
    ) {
      return this.defaultAvailableDorm.concat(this.defaultAvailableDorm);
    }

    return this.defaultAvailableDorm.concat(this.availableDorms).concat({
      id: dormAdministrator.dormId,
      dormName: dormAdministrator.dormName,
    });
  }

  public editDormAdministrator(
    dormAdministrator: DetailedDormAdministratorDto
  ): void {
    this.editDormAdministratorNewDormId = dormAdministrator.dormId;
  }

  public saveEditedDormAdministrator(
    dormAdministratorDto: DetailedDormAdministratorDto
  ): void {
    let updateDormAdministratorDto: UpdateDormAdministratorDto = {
      dormId: this.editDormAdministratorNewDormId,
      administratorEmail: dormAdministratorDto.email,
    };

    this.loading = true;
    this.dormAdministratorDetailsService
      .updateDormAdministratorAssociatedDorm(updateDormAdministratorDto)
      .subscribe({
        next: () => {
          dormAdministratorDto.dormId = this.editDormAdministratorNewDormId;
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Dorm administrator updated',
          });
          this.updateDormAdministratorsList();
          this.loading = false;
        },
        error: (error) => {
          console.error(error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail:
              'Failed to update dorm administrator: ' + error?.error?.message,
          });
          this.loading = false;
        },
      });
  }

  public deleteDormAdministrator(
    dormAdministrator: DetailedDormAdministratorDto
  ): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this dorm administrator?',
      accept: () => {
        let emailDto: EmailDto = { email: dormAdministrator.email };
        this.dormAdministratorDetailsService
          .deleteDormAdministrator(emailDto)
          .subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Dorm administrator deleted',
              });
              this.updateDormAdministratorsList();
            },
            error: (error) => {
              console.error(error);
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail:
                  'Failed to delete dorm administrator: ' + error.error.message,
              });
            },
          });
      },
    });
  }

  getOverlayOptions(): OverlayOptions {
    return {
      listener: (event: Event, options?: OverlayListenerOptions) => {
        if (options?.type === 'scroll') {
          return false;
        }
        return options?.valid;
      },
    };
  }
}
