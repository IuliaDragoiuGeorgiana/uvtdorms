import { Component } from '@angular/core';
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

  constructor(
    private userService: UserService,
    private registerRequestService: RegisterRequestService,
    private roomService: RoomService,
    private dormService: DormService,
    private laundryAppointmentService: AppointmentService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.userService.getUserDetails().subscribe({
      next: (userDetails) => {
        this.user = userDetails;
      },
      error(err) {
        console.error(err);
      },
    });

    this.registerRequestService.getRegisterRequestsForStudent().subscribe({
      next: (value) => {
        this.registerRequests = value;
      },
      error(err) {
        console.error(err);
      },
    });

    this.laundryAppointmentService.getStudentLaundryAppointments().subscribe({
      next: (value) => {
        this.studentLaundryAppointments = value;
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
}
