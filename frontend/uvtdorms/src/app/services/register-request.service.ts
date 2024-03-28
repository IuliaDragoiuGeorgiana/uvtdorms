import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { RegisterRequestDto } from '../interfaces/register-request-dto';
import { Observable } from 'rxjs';
import { ListedRegisterRequestDto } from '../interfaces/listed-register-request-dto';
import { NewRegisterRequestDto } from '../interfaces/new-register-request-dto';

@Injectable({
  providedIn: 'root',
})
export class RegisterRequestService {
  private registerRequestServiceUrl: string =
    'http://localhost:8080/api/register-requests';
  private getRegisterRequestsFromDromUrl: string =
    '/get-register-requests-from-dorm';
  private acceptRegisterRequestUrl: string = '/accept-register-request';
  private declineRegisterRequestUrl: string = '/decline-register-request';
  private registerRequestsForStudentUrl: string =
    '/register-requests-for-student';
  private createNewRegisterRequestUrl: string = '/new-register-request';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getRegisterRequestsFromDrom(): Observable<RegisterRequestDto[]> {
    return this.http.get<RegisterRequestDto[]>(
      this.registerRequestServiceUrl + this.getRegisterRequestsFromDromUrl,
      { headers: this.auth.getHeader() }
    );
  }

  acceptRegisterRequest(
    registerRequestDto: RegisterRequestDto
  ): Observable<void> {
    return this.http.post<void>(
      this.registerRequestServiceUrl + this.acceptRegisterRequestUrl,
      registerRequestDto,
      { headers: this.auth.getHeader() }
    );
  }

  declineRegisterRequest(
    registerRequestDto: RegisterRequestDto
  ): Observable<void> {
    return this.http.post<void>(
      this.registerRequestServiceUrl + this.declineRegisterRequestUrl,
      registerRequestDto,
      { headers: this.auth.getHeader() }
    );
  }

  getRegisterRequestsForStudent(): Observable<ListedRegisterRequestDto[]> {
    return this.http.get<ListedRegisterRequestDto[]>(
      this.registerRequestServiceUrl + this.registerRequestsForStudentUrl,
      { headers: this.auth.getHeader() }
    );
  }

  createNewRegisterRequest(
    newRegisterRequestDto: NewRegisterRequestDto
  ): Observable<void> {
    return this.http.post<void>(
      this.registerRequestServiceUrl + this.createNewRegisterRequestUrl,
      newRegisterRequestDto,
      { headers: this.auth.getHeader() }
    );
  }
}
