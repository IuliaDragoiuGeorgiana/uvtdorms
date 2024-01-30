import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  hide = true;

  constructor(private authService: AuthService){}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  onLogin() {
    if (!this.loginForm.valid) {
      return;
    }

    this.authService.login({email: this.email?.value, password: this.password?.value}).subscribe({
      next: (tokenDto) => {
        this.authService.setAuthToken(tokenDto.token);
      }
    });
  }
}

