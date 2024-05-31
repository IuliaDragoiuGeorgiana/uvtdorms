import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { DisplayDormAdministratorDetailsDto } from '../interfaces/display-dorm-administrator-details-dto';
import { DormId } from '../interfaces/dorm-id';
import { DormAdministratorDto } from '../interfaces/dorm-administrator-dto';

@Injectable({
  providedIn: 'root',
})
export class DormAdministratorDetailsService {
  private dormAdministratorDetailsServiceUrl =
    'http://localhost:8080/api/dorm-administrator-details';
  private getDormAdministratorDetailsUrl = '/get-administrator-details';
  private getAdministratedDormIdUrl = '/get-administrated-dorm-id';
  private getAvailableDormAdministratorsUrl =
    '/get-available-dorm-administrators';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getDormAdministratorDetails(): Observable<DisplayDormAdministratorDetailsDto> {
    return this.http.get<DisplayDormAdministratorDetailsDto>(
      this.dormAdministratorDetailsServiceUrl +
        this.getDormAdministratorDetailsUrl,
      { headers: this.auth.getHeader() }
    );
  }

  getAdministratedDormId(): Observable<DormId> {
    return this.http.get<DormId>(
      this.dormAdministratorDetailsServiceUrl + this.getAdministratedDormIdUrl,
      { headers: this.auth.getHeader() }
    );
  }

  getAvailableDormAdministrators(): Observable<DormAdministratorDto[]> {
    return this.http.get<DormAdministratorDto[]>(
      this.dormAdministratorDetailsServiceUrl +
        this.getAvailableDormAdministratorsUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
