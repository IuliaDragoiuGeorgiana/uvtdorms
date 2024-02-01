import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenDto } from '../interfaces/token-dto';
import { RegisterStudentDto } from '../interfaces/register-student-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authServiceUrl: string = 'http://localhost:8080/api/auth';
  private loginUrl: string = '/login';
  private loginWithTokenUrl: string = '/login-with-token';
  private registerUrl: string = '/register-student';

  constructor(private http: HttpClient) {}

  getAuthToken(): string | null {
    if (typeof window !== 'undefined') {
      return window.localStorage.getItem('uvtdorms_auth_token');
    } else {
      return null;
    }
  }

  setAuthToken(token: string | null) {
    if (token !== null) {
      window.localStorage.setItem('uvtdorms_auth_token', token);
    } else {
      window.localStorage.removeItem('uvtdorms_auth_token');
    }
  }

  getHeader(): HttpHeaders {
    let headers = new HttpHeaders();

    let authToken = window.localStorage.getItem('uvtdorms_auth_token');

    if (authToken !== null) {
      headers = headers.set('Authorization', `Bearer ${authToken}`);
    }

    return headers;
  }

  login(data: {}): Observable<TokenDto> {
    return this.http.post<TokenDto>(this.authServiceUrl + this.loginUrl, data);
  }

  loginWithToken(): Observable<TokenDto> {
    return this.http.get<TokenDto>(
      this.authServiceUrl + this.loginWithTokenUrl,
      { headers: this.getHeader() }
    );
  }

  registerStudent(registerStudentDto: RegisterStudentDto): Observable<void> {
    return this.http.post<void>(
      this.authServiceUrl + this.registerUrl,
      registerStudentDto
    );
  }
}
