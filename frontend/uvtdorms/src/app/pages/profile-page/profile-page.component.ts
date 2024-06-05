import { Component, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserDetailsDto } from '../../interfaces/user-details-dto';
import { ListedRegisterRequestDto } from '../../interfaces/listed-register-request-dto';
import { RegisterRequestService } from '../../services/register-request.service';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { RoomService } from '../../services/room.service';
import { DormService } from '../../services/dorm.service';
import { MatDialog } from '@angular/material/dialog';
import { NewRegisterRequestDialogComponent } from '../../elements/dialogs/new-register-request-dialog/new-register-request-dialog.component';
import { Role } from '../../enums/role';
import { EditPhoneNumberDialogComponent } from '../../elements/dialogs/edit-phone-number-dialog/edit-phone-number-dialog.component';
import { ChangePasswordDialogComponent } from '../../elements/dialogs/change-password-dialog/change-password-dialog.component';
import { ChangeProfilePictureDto } from '../../interfaces/change-profile-picture-dto';
import { AppointmentService } from '../../services/appointment.service';
import { StudentLaundryAppointmentsDto } from '../../interfaces/student-laundry-appointments-dto';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TicketService } from '../../services/ticket.service';
import { StudentTicketsDto } from '../../interfaces/student-tickets-dto';
import { EvenimentDto } from '../../interfaces/eveniment-dto';
import { EvenimentService } from '../../services/eveniment.service';
import { Router } from '@angular/router';
import { CreateEvenimentDto } from '../../interfaces/create-eveniment-dto';
import { IdDto } from '../../interfaces/id-dto';
import { Editor } from 'primeng/editor';
import { UpdateEvenimentDto } from '../../interfaces/update-eveniment-dto';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
})
export class ProfilePageComponent {
  public user: UserDetailsDto | undefined = undefined;
  public registerRequests: ListedRegisterRequestDto[] = [];
  public isRegisterRequestDialogVisible: boolean = false;
  public studentLaundryAppointments: StudentLaundryAppointmentsDto[] = [];
  public studentTickets: StudentTicketsDto[] = [];

  public eveniments: EvenimentDto[] = [];

  public editEvenimentDto: EvenimentDto | undefined = undefined;
  public isCreateEvenimentDialogVisible: boolean = false;
  public newEvenimentForm: FormGroup = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]),
    description: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(1500),
    ]),
    startDate: new FormControl<Date>(new Date(), [Validators.required]),
    canPeopleAttend: new FormControl<boolean>(false),
  });
  public minDate = new Date();
  public isLoadingScreenVisible: boolean = true;

  get hasNewEvenimentTitleError(): boolean {
    const control = this.newEvenimentForm.controls['title'];
    return control.touched && control.invalid;
  }

  get hasNewEvenimentDescriptionError(): boolean {
    const control = this.newEvenimentForm.controls['description'];
    return control.touched && control.invalid;
  }

  get hasNewEvenimentStartDateError(): boolean {
    const control = this.newEvenimentForm.controls['startDate'];
    return control.touched && control.invalid;
  }

  public isEditEvenimentDialogVisible: boolean = false;
  public editEvenimentForm: FormGroup = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]),
    description: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(1500),
    ]),
    startDate: new FormControl<Date>(new Date(), [Validators.required]),
    canPeopleAttend: new FormControl<boolean>(false, [Validators.required]),
  });

  get hasEditEvenimentTitleError(): boolean {
    const control = this.editEvenimentForm.controls['title'];
    return control.touched && control.invalid;
  }

  get hasEditEvenimentDescriptionError(): boolean {
    const control = this.editEvenimentForm.controls['description'];
    return control.touched && control.invalid;
  }

  get hasEditEvenimentStartDateError(): boolean {
    const control = this.editEvenimentForm.controls['startDate'];
    return control.touched && control.invalid;
  }

  constructor(
    private userService: UserService,
    private registerRequestService: RegisterRequestService,
    private ticketService: TicketService,
    private roomService: RoomService,
    private dormService: DormService,
    private laundryAppointmentService: AppointmentService,
    private dialog: MatDialog,
    private confirmService: ConfirmationService,
    private messageService: MessageService,
    private evenimentService: EvenimentService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userService.getUserDetails().subscribe({
      next: (userDetails) => {
        this.user = userDetails;

        if (this.isStudent() || this.isInactivStudent()) {
          this.getStudentRelatedData();
        }

        if (this.isDormAdmin()) {
          this.getDormAdministratorRelatedData();
        }
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getDormAdministratorRelatedData() {
    let l1 = false;
    this.evenimentService.getEvenimentsFromDrom().subscribe({
      next: (eveniments) => {
        this.eveniments = eveniments;
        l1 = true;
        if (l1) {
          this.isLoadingScreenVisible = false;
        }
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  private getStudentRelatedData() {
    let l1 = false;
    let l2 = false;
    let l3 = false;
    this.registerRequestService.getRegisterRequestsForStudent().subscribe({
      next: (value) => {
        this.registerRequests = value;
        l1 = true;
        if (l1 && l2 && l3) {
          this.isLoadingScreenVisible = false;
        }
      },
      error(err) {
        console.error(err);
      },
    });

    this.laundryAppointmentService.getStudentLaundryAppointments().subscribe({
      next: (value) => {
        this.studentLaundryAppointments = value;
        l2 = true;
        if (l1 && l2 && l3) {
          this.isLoadingScreenVisible = false;
        }
      },
      error(err) {
        console.error(err);
      },
    });

    this.ticketService.getStudentTickets().subscribe({
      next: (value) => {
        console.log(value);
        this.studentTickets = value;
        l3 = true;
        if (l1 && l2 && l3) {
          this.isLoadingScreenVisible = false;
        }
      },
      error(err) {
        console.error(err);
      },
    });
  }

  getRequestStatusSeverity(status: string) {
    switch (status) {
      case 'ACCEPTED':
        return 'success';
      case 'DECLINED':
        return 'danger';
      case 'RECEIVED':
        return 'warning';
      default:
        return 'info';
    }
  }

  getLaundryStatusSeverity(status: string) {
    switch (status) {
      case 'SCHEDULED':
        return 'in progress';
      case 'COMPLETED':
        return 'done';
      case 'CANCELED':
        return 'danger';
      default:
        return 'info';
    }
  }

  getTicketStatusSeverity(status: string) {
    switch (status) {
      case 'OPEN':
        return 'in progress';
      case 'CLOSED':
        return 'done';
      default:
        return 'info';
    }
  }

  public formatLaundryAppoitnmentDate(date: any): string {
    return (
      date[0] +
      '/' +
      date[1] +
      '/' +
      date[2] +
      ' ' +
      (Number(date[3]) <= 9 ? '0' + date[3] : date[3]) +
      ':' +
      (Number(date[4]) <= 9 ? '0' + date[4] : date[4])
    );
  }

  showRegisterRequestDialog() {
    this.dialog.open(NewRegisterRequestDialogComponent);
  }

  role(): Role | null {
    return this.userService.getRole();
  }

  isStudent(): boolean {
    return this.role() === Role.STUDENT;
  }

  isDormAdmin(): boolean {
    return this.role() === Role.ADMINISTRATOR;
  }

  isInactivStudent(): boolean {
    return this.role() === Role.INACTIV_STUDENT;
  }

  openEditPhoneNumberDialog() {
    this.dialog.open(EditPhoneNumberDialogComponent);
  }

  openChangePasswordDialog() {
    this.dialog.open(ChangePasswordDialogComponent);
  }

  private extractBase64Data(base64String: string): string {
    const commaIndex = base64String.indexOf(',');
    if (commaIndex !== -1 && commaIndex + 1 < base64String.length) {
      return base64String.substring(commaIndex + 1);
    }

    return base64String;
  }

  public onFilesSelected(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      const base64String = e.target?.result as string;
      const base64Data = this.extractBase64Data(base64String);
      let changeProfilePictureDto: ChangeProfilePictureDto = {
        image: base64Data,
      };
      this.userService.updateProfilePicture(changeProfilePictureDto).subscribe({
        next: () => {
          console.log('Profile picture updated');
          window.location.reload();
        },
        error(err) {
          console.error(err);
        },
      });
    };
    reader.readAsDataURL(file);
  }

  confirmDeletion(event: Event) {
    this.confirmService.confirm({
      target: event.target as EventTarget,
      message: 'Are you sure that you want to delete this appointment?',
      icon: 'pi pi-exclamation-triangle',
      header: 'Confirmation',
      acceptButtonStyleClass: 'p-button-danger p-button-text',
      rejectButtonStyleClass: 'p-button-text p-button-text',
      acceptIcon: 'none',
      rejectIcon: 'none',

      accept: () => {
        this.deleteAppointment();
        console.log('Appointment deleted1');
      },
    });
  }

  deleteAppointment() {
    this.laundryAppointmentService.deleteAppointment().subscribe({
      next: () => {
        console.log('Appointment deleted2');
        this.messageService.add({
          severity: 'info',
          summary: 'Confirmed',
          detail: 'You have deleted the appointment',
        });
        window.location.reload();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  public formatDate(date: any[]): string {
    return (
      date[0] +
      '/' +
      (date[1] < 10 ? '0' + date[1] : date[1]) +
      '/' +
      (date[2] < 10 ? '0' + date[2] : date[2]) +
      ' ' +
      (date[3] < 10 ? '0' + date[3] : date[3]) +
      ':' +
      (date[4] < 10 ? '0' + date[4] : date[4])
    );
  }

  createEveniment(): void {
    this.newEvenimentForm.markAllAsTouched();
    this.newEvenimentForm.markAsDirty();
    this.newEvenimentForm.updateValueAndValidity();

    console.log(this.newEvenimentForm.value);

    if (this.newEvenimentForm.invalid) {
      return;
    }

    this.isLoadingScreenVisible = true;

    let createEvenimentDto: CreateEvenimentDto = {
      title: this.newEvenimentForm.get('title')?.value,
      description: this.newEvenimentForm.get('description')?.value,
      canPeopleAttend: this.newEvenimentForm.get('canPeopleAttend')?.value,
      startDate: this.newEvenimentForm.get('startDate')?.value,
    };

    this.evenimentService.createEveniment(createEvenimentDto).subscribe({
      next: () => {
        this.isCreateEvenimentDialogVisible = false;
        this.newEvenimentForm.reset();
        this.newEvenimentForm.value.startDate = new Date();
        this.getDormAdministratorRelatedData();
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Eveniment created successfully',
        });
      },
      error: (error) => {
        console.error(error);
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Could not create the eveniment',
        });
      },
    });
  }

  private deleteEvenimentConfirmed(eveniment: EvenimentDto) {
    this.isLoadingScreenVisible = true;
    let idDto: IdDto = {
      id: eveniment.id,
    };
    this.evenimentService.deleteEveniment(idDto).subscribe({
      next: () => {
        this.getDormAdministratorRelatedData();
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Eveniment deleted successfully',
        });
      },
      error: (error) => {
        console.error(error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Could not delete the eveniment',
        });
      },
    });
  }

  public deleteEveniment(eveniment: EvenimentDto) {
    this.confirmService.confirm({
      message: 'Are you sure you want to delete this eveniment?',
      accept: () => {
        this.deleteEvenimentConfirmed(eveniment);
      },
    });
  }

  @ViewChild('editor') editor!: Editor;

  public openEditEvenimentDialog(eveniment: EvenimentDto) {
    this.editEvenimentDto = eveniment;

    console.log(this.editEvenimentDto);

    this.editEvenimentForm.get('title')?.setValue(eveniment.title);
    this.editEvenimentForm.get('description')?.setValue(eveniment.description);
    this.editor
      .getQuill()
      .clipboard.dangerouslyPasteHTML(eveniment.description);
    let d: Date = new Date();
    d.setFullYear(Number(eveniment.startDate[0]));
    d.setMonth(Number(eveniment.startDate[1]) - 1);
    d.setDate(Number(eveniment.startDate[2]));
    d.setHours(Number(eveniment.startDate[3]));
    d.setMinutes(Number(eveniment.startDate[4]));
    this.editEvenimentForm.get('startDate')?.setValue(d);
    this.editEvenimentForm
      .get('canPeopleAttend')
      ?.setValue(eveniment.canPeopleAttend);

    this.isEditEvenimentDialogVisible = true;
  }

  public updateEditedEveniment() {
    this.editEvenimentForm.markAllAsTouched();
    this.editEvenimentForm.markAsDirty();
    this.editEvenimentForm.markAsPristine();
    this.editEvenimentForm.updateValueAndValidity();
    this.editEvenimentForm.markAllAsTouched();
    console.log(this.editEvenimentForm.value);
    console.log(this.editEvenimentForm.invalid);
    console.log(this.hasEditEvenimentDescriptionError);
    console.log(this.hasEditEvenimentStartDateError);
    console.log(this.hasEditEvenimentTitleError);
    if (this.editEvenimentForm.invalid) {
      return;
    }

    this.isLoadingScreenVisible = true;

    let updateEvenimentDto: UpdateEvenimentDto = {
      id: this.editEvenimentDto?.id!,
      title: this.editEvenimentForm.get('title')?.value,
      description: this.editEvenimentForm.get('description')?.value,
      canPeopleAttend: this.editEvenimentForm.get('canPeopleAttend')?.value,
      startDate: this.editEvenimentForm.get('startDate')?.value,
    };

    console.log(updateEvenimentDto);

    this.evenimentService.updateEveniment(updateEvenimentDto).subscribe({
      next: () => {
        this.isEditEvenimentDialogVisible = false;
        this.editEvenimentForm.reset();
        this.getDormAdministratorRelatedData();
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Eveniment updated successfully',
        });
      },
      error: (error) => {
        console.error(error);
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Could not update the eveniment',
        });
      },
    });
  }
}
