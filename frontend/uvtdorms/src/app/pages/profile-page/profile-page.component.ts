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

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
})
export class ProfilePageComponent {
  public user: UserDetailsDto | undefined = undefined;
  public registerRequests: ListedRegisterRequestDto[] = [];
  public isRegisterRequestDialogVisible: boolean = false;

  constructor(
    private userService: UserService,
    private registerRequestService: RegisterRequestService,
    private roomService: RoomService,
    private dormService: DormService,
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

  showRegisterRequestDialog() {
    this.dialog.open(NewRegisterRequestDialogComponent);
  }
}
