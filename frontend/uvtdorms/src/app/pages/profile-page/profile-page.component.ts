import { Component, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserDetailsDto } from '../../interfaces/user-details-dto';
import { ListedRegisterRequestDto } from '../../interfaces/listed-register-request-dto';
import { RegisterRequestService } from '../../services/register-request.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
import { CreateEvenimentDto } from '../../interfaces/create-eveniment-dto';
import { IdDto } from '../../interfaces/id-dto';
import { Editor } from 'primeng/editor';
import { UpdateEvenimentDto } from '../../interfaces/update-eveniment-dto';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';
import { StatisticsCountDto } from '../../interfaces/statistics-count-dto';
import { StudentDetailsService } from '../../services/student-details.service';
import { TranslateService } from '@ngx-translate/core';

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
  public statistics: StatisticsCountDto | undefined = undefined;

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

  public data = {
    labels: [
      'Number of Dorm Administrators',
      'Number of Dorms',
      'Number of Students',
    ],
    datasets: [
      {
        data: [0, 0, 0]
      },
    ],
  };

  public options: any = {
    plugins: {
        legend: {
            labels: {
                usePointStyle: true,
                color: 'rgba(0, 0, 0, 1)'
            }
        }
    },
    responsive: true,
    maintainAspectRatio: false
  };

  public isOverviewChartVisible: boolean = false;

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

  get isMobileScreen(): boolean {
    return window.innerWidth <= 600;
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
    private studentDetails: StudentDetailsService,
    private dormService: DormService,
    private dormAdministratorService: DormAdministratorDetailsService,
    private registerRequestService: RegisterRequestService,
    private ticketService: TicketService,
    private laundryAppointmentService: AppointmentService,
    private dialog: MatDialog,
    private confirmService: ConfirmationService,
    private messageService: MessageService,
    private evenimentService: EvenimentService,
    private translate: TranslateService
  ) {}

  ngOnInit() {
    this.userService.getUserDetails().subscribe({
      next: (userDetails) => {
        this.user = userDetails;

        if (this.isStudent() || this.isInactivStudent()) {
          this.getStudentRelatedData();
        } else if (this.isDormAdmin()) {
          this.getDormAdministratorRelatedData();
        } else if (this.isApplicationAdministrator()) {
          this.getApplicationAdministratorRelatedData();
        } else {
          console.log('x');
        }
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getApplicationAdministratorRelatedData() {
    console.log('hello world');
    let l1 = false;
    let l2 = false;
    let l3 = false;
    this.dormAdministratorService.getNumberOfDormAdministrators().subscribe({
      next: (statistics) => {
        this.data.datasets[0].data[0] = statistics.count;
        l1 = true;
        if (l1 && l2 && l3) {
          console.log(this.data);
          this.isOverviewChartVisible = true;
          this.isLoadingScreenVisible = false;
        }
      },
      error(err) {
        console.error(err);
      },
    });

    this.dormService.getNumberOfDorms().subscribe({
      next: (statistics) => {
        this.data.datasets[0].data[1] = statistics.count;
        l2 = true;
        if (l1 && l2 && l3) {
          console.log(this.data);
          this.isOverviewChartVisible = true;
          this.isLoadingScreenVisible = false;
        }
      },
      error(err) {
        console.error(err);
      },
    });

    this.studentDetails.getNumberOfStudents().subscribe({
      next: (statistics) => {
        this.data.datasets[0].data[2] = statistics.count;
        l3 = true;
        if (l1 && l2 && l3) {
          console.log(this.data);
          this.isOverviewChartVisible = true;
          this.isLoadingScreenVisible = false;
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

  getLabelTicketStatus(status: string): string {
    switch (status) {
      case 'OPEN':
        return this.translate.instant('ticketsAdministrationPage.status.open');
      case 'RESOLVED':
        return this.translate.instant(
          'ticketsAdministrationPage.status.resolved'
        );
      default:
        return 'Unknown Status';
    }
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
        return 'info';
      case 'COMPLETED':
        return 'success';
      case 'CANCELED':
        return 'danger';
      default:
        return 'info';
    }
  }

  getTicketStatusSeverity(status: string) {
    switch (status) {
      case 'OPEN':
        return 'danger';
      case 'RESOLVED':
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

  public formatTicketDate(date: any): string {
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

  isApplicationAdministrator(): boolean {
    return this.role() === Role.APPLICATION_ADMINISTRATOR;
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
      message: this.translate.instant(
        'profile.laundryAppointmentConfirmDeleteMessage'
      ),
      icon: 'pi pi-exclamation-triangle',
      header: this.translate.instant(
        'profile.laundryAppointmentConfirmDeleteHeader'
      ),
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
        this.messageService.add({
          severity: 'info',
          summary: this.translate.instant(
            'profile.laundryAppointmentDeleteSuccesMessageHeader'
          ),
          detail: this.translate.instant(
            'profile.laundryAppointmentDeleteSuccesMessage'
          ),
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
          summary: this.translate.instant(
            'profile.createEventSuccessMessageHeader'
          ),
          detail: this.translate.instant('profile.createEventSuccessMessage'),
        });
      },
      error: (error) => {
        console.error(error);
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'profile.createEventErrorMessageHeader'
          ),
          detail: this.translate.instant('profile.createEventErrorMessage'),
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
          summary: this.translate.instant(
            'profile.deleteEventSuccesMessageHeader'
          ),
          detail: this.translate.instant('profile.deleteEventSuccesMessage'),
        });
      },
      error: (error) => {
        console.error(error);
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'profile.deleteEventErrorMessageHeader'
          ),
          detail: this.translate.instant('profile.deleteEventErrorMessage'),
        });
      },
    });
  }

  public deleteEveniment(eveniment: EvenimentDto) {
    this.confirmService.confirm({
      message: this.translate.instant('profile.delete.ConfirmMessage'),
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
          summary: this.translate.instant(
            'profilPage.updateEveniment.popup.success.summary'
          ),
          detail: this.translate.instant(
            'profilPage.updateEveniment.popup.success.detail'
          ),
        });
      },
      error: (error) => {
        console.error(error);
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'profilPage.updateEveniment.popup.error.summary'
          ),
          detail: this.translate.instant(
            'profilPage.updateEveniment.popup.error.detail'
          ),
        });
      },
    });
  }
}
