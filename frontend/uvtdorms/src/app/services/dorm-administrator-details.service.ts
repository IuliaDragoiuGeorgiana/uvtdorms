import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { DisplayDormAdministratorDetailsDto } from '../interfaces/display-dorm-administrator-details-dto';
import { DormId } from '../interfaces/dorm-id';
import { DormAdministratorDto } from '../interfaces/dorm-administrator-dto';
import { AddNewDormAdministratorDto } from '../interfaces/add-new-dorm-administrator-dto';
import { UpdateDormAdministratorDto } from '../interfaces/update-dorm-administrator-dto';
import { EmailDto } from '../interfaces/email-dto';
import { DetailedDormAdministratorDto } from '../interfaces/detailed-dorm-administrator-dto';
import { StatisticsCountDto } from '../interfaces/statistics-count-dto';

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
  private getAllDormAdministratorsUrl = '/get-all-dorm-administrators';
  private addDormAdministratorUrl = '/add-new-dorm-administrator';
  private updateDormAdministratorAssociatedDormUrl =
    '/update-dorm-administrator-associated-dorm';
  private deleteDormAdministratorUrl = '/delete-dorm-administrator';
  private getNumberOfDormAdministratorsUrl = '/get-number-of-dorm-administrators';
  private hasDormAdministratorAssociatedDormUrl = '/has-dorm-administrator-associated-dorm';

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

  getAllDormAdministrators(): Observable<DetailedDormAdministratorDto[]> {
    return this.http.get<DetailedDormAdministratorDto[]>(
      this.dormAdministratorDetailsServiceUrl +
        this.getAllDormAdministratorsUrl,
      { headers: this.auth.getHeader() }
    );
  }

  addNewDormAdministrator(
    addNewDormAdministratorDto: AddNewDormAdministratorDto
  ): Observable<void> {
    return this.http.post<void>(
      this.dormAdministratorDetailsServiceUrl + this.addDormAdministratorUrl,
      addNewDormAdministratorDto,
      { headers: this.auth.getHeader() }
    );
  }

  updateDormAdministratorAssociatedDorm(
    updateDormAdministratorDto: UpdateDormAdministratorDto
  ): Observable<void> {
    return this.http.post<void>(
      this.dormAdministratorDetailsServiceUrl +
        this.updateDormAdministratorAssociatedDormUrl,
      updateDormAdministratorDto,
      { headers: this.auth.getHeader() }
    );
  }

  deleteDormAdministrator(emailDto: EmailDto): Observable<void> {
    return this.http.post<void>(
      this.dormAdministratorDetailsServiceUrl + this.deleteDormAdministratorUrl,
      emailDto,
      { headers: this.auth.getHeader() }
    );
  }

  getNumberOfDormAdministrators(): Observable<StatisticsCountDto> {
    return this.http.get<StatisticsCountDto>(
      this.dormAdministratorDetailsServiceUrl +
        this.getNumberOfDormAdministratorsUrl,
      { headers: this.auth.getHeader() }
    );
  }

  hasDormAdministratorAssociatedDorm(): Observable<boolean> {
    return this.http.get<boolean>(
      this.dormAdministratorDetailsServiceUrl +
        this.hasDormAdministratorAssociatedDormUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
