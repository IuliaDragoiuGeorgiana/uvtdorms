import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { DisplayDormAdministratorDetailsDto } from '../interfaces/display-dorm-administrator-details-dto';

@Injectable({
  providedIn: 'root',
})
export class DormAdministratorDetailsService {
  private dormAdministratorDetailsServiceUrl =
    'http://localhost:8080/api/dorm-administrator-details';
  private getDormAdministratorDetailsUrl = '/get-administrator-details';
  constructor(private http: HttpClient, private auth: AuthService) {}

  getDormAdministratorDetails(): Observable<DisplayDormAdministratorDetailsDto> {
    return this.http.get<DisplayDormAdministratorDetailsDto>(
      this.dormAdministratorDetailsServiceUrl +
        this.getDormAdministratorDetailsUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
