import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { convertStringRoleToEnum } from '../../enums/role';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent {
  hide = true;
  showLogin = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private userService: UserService
  ) {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    dormName: new FormControl('', [Validators.required]),
    roomNumber: new FormControl('', [Validators.required, Validators.min(0)]),
    matriculationNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('^[A-Za-z]{1,3}\\d{1,4}$'),
    ]),
    phoneNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-s./0-9]*$'),
    ]),
  });

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
          this.router.navigate(['laundry-appointments']);
        },
      });
  }

  onRegister() {}
}
