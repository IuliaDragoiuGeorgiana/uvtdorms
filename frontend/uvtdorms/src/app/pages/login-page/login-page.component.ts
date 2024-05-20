import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { convertStringRoleToEnum } from '../../enums/role';
import { DormService } from '../../services/dorm.service';
import { RoomService } from '../../services/room.service';
import { RegisterStudentDto } from '../../interfaces/register-student-dto';
import { RegisterDialogData } from '../../interfaces/register-dialog-data';
import { MatDialog } from '@angular/material/dialog';
import { RegisterErrorDialogComponent } from '../../elements/dialogs/register/register-error-dialog/register-error-dialog.component';
import { RegisterConfirmDialogComponent } from '../../elements/dialogs/register/register-confirm-dialog/register-confirm-dialog.component';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent {
  hide = true;
  showLogin = true;
  isLoadingOverlayVisible = false;
  dormsNames: string[] = [];
  private validRoomNumbers: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private userService: UserService,
    private dormService: DormService,
    private roomService: RoomService,
    private dialog: MatDialog,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.dormService.getDormNames().subscribe({
      next: (dormNames) => {
        this.dormsNames = dormNames.names;
      },
    });
  }

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    dormName: new FormControl('', [Validators.required]),
    roomNumber: new FormControl('', [
      Validators.required,
      this.roomNumberValidator(),
    ]),
    matriculationNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('^[A-Za-z]{1,3}\\d{1,4}$'),
    ]),
    phoneNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-s./0-9]*$'),
    ]),
  });

  roomNumberValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const isValid = this.validRoomNumbers.includes(control.value.toString());
      return isValid ? null : { invalidRoomNumber: { value: control.value } };
    };
  }

  get loginEmail() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  get registerEmail() {
    return this.registerForm.get('email');
  }

  get firstName() {
    return this.registerForm.get('firstName');
  }

  get lastName() {
    return this.registerForm.get('lastName');
  }

  get roomNumber() {
    return this.registerForm.get('roomNumber');
  }

  get matriculationNumber() {
    return this.registerForm.get('matriculationNumber');
  }

  get phoneNumber() {
    return this.registerForm.get('phoneNumber');
  }

  onDormSelected() {
    let selectedDormName: string = this.registerForm.get('dormName')?.value!;
    this.roomService.getRoomsNumbersFromDrom(selectedDormName).subscribe({
      next: (roomsNumbers) => {
        this.validRoomNumbers = roomsNumbers.numbers;
      },
    });
  }

  onLogin() {
    if (!this.loginForm.valid) {
      return;
    }

    this.authService
      .login({ email: this.loginEmail?.value, password: this.password?.value })
      .subscribe({
        next: (tokenDto) => {
          this.authService.setAuthToken(tokenDto.token);
          this.userService.setRole(convertStringRoleToEnum(tokenDto.role)!);
          this.router.navigate(['/profile']);
        },
        error: () => {
          this.showUnsuccessfulLoginMessage();
        },
      });
  }

  onRegister() {
    if (!this.registerForm.valid) return;

    let registerStudentDto: RegisterStudentDto =
      this.convertRegisterFormToRegisterStudentDto();
    this.isLoadingOverlayVisible = true;
    this.authService.registerStudent(registerStudentDto).subscribe({
      next: () => {
        this.isLoadingOverlayVisible = false;
        this.dialog.open(RegisterConfirmDialogComponent);
        this.showLogin = !this.showLogin;
      },
      error: (error) => {
        this.isLoadingOverlayVisible = false;
        let registerDialogData: RegisterDialogData = {
          message: error.error.message,
        };

        this.dialog.open(RegisterErrorDialogComponent, {
          data: registerDialogData,
        });
      },
    });
  }

  convertRegisterFormToRegisterStudentDto(): RegisterStudentDto {
    let registerStudentDto: RegisterStudentDto = {
      email: this.registerEmail?.value!,
      firstName: this.firstName?.value!,
      lastName: this.lastName?.value!,
      dormName: this.registerForm.get('dormName')?.value!,
      roomNumber: this.roomNumber?.value!,
      matriculationNumber: this.matriculationNumber?.value!,
      phoneNumber: this.phoneNumber?.value!,
    };

    return registerStudentDto;
  }

  private showUnsuccessfulLoginMessage() {
    this.messageService.add({
      severity: 'error',
      summary: 'Something went wrong',
      detail: 'Please check your email and password and try again.',
    });
  }

  public redirectToForgotPassword() {
    this.router.navigate(['/forgot-password']);
  }
}
