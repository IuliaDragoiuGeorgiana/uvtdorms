import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AxiosService } from '../../services/axios.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  hide = true;

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
  constructor(private axiosService: AxiosService){}

  onLogin() {
    // Add login logic here
    console.log(this.loginForm.value);
    if (!this.loginForm.valid) {
      return;
    }
    this.axiosService.request(
      "POST",
      "/login",
      {
        "email": this.email?.value,
        "password": this.password?.value
      }
    ).then(
      (response) => {
        console.log(response);
        this.axiosService.setAuthToken(response.data.token);
      }
    );
  }
}

