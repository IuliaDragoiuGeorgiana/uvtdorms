import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { RegisterRequestDto } from '../interfaces/register-request-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegisterRequestService {
  private registerRequestServiceUrl: string =
    'http://localhost:8080/api/register-requests';
  private getRegisterRequestsFromDromUrl: string =
    '/get-register-requests-from-dorm';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getRegisterRequestsFromDrom(): Observable<RegisterRequestDto[]> {
    return this.http.get<RegisterRequestDto[]>(
      this.registerRequestServiceUrl + this.getRegisterRequestsFromDromUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
