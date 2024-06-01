import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DormNamesDto } from '../interfaces/dorm-names';
import { DormDto } from '../interfaces/dorm-dto';
import { UpdateDormAdministratorDto } from '../interfaces/update-dorm-administrator-dto';
import { DormId } from '../interfaces/dorm-id';

@Injectable({
  providedIn: 'root',
})
export class DormService {
  private dromServiceUrl = 'http://localhost:8080/api/dorms';
  private getDormNamesUrl = '/dorms-names';
  private getAllDormsUrl = '/all-dorms';
  private addDormUrl = '/add-dorm';
  private updateDormAdministratorUrl = '/update-dorm-administrator';
  private deleteDormUrl = '/delete-dorm';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getDormNames(): Observable<DormNamesDto> {
    return this.http.get<DormNamesDto>(
      this.dromServiceUrl + this.getDormNamesUrl,
      { headers: this.auth.getHeader() }
    );
  }

  getDorms(): Observable<DormDto[]> {
    return this.http.get<DormDto[]>(this.dromServiceUrl + this.getAllDormsUrl, {
      headers: this.auth.getHeader(),
    });
  }

  addDorm(dormDto: DormDto): Observable<void> {
    return this.http.post<void>(
      this.dromServiceUrl + this.addDormUrl,
      dormDto,
      { headers: this.auth.getHeader() }
    );
  }

  updateDormAdministrator(
    updateDormAdministratorDto: UpdateDormAdministratorDto
  ): Observable<void> {
    return this.http.post<void>(
      this.dromServiceUrl + this.updateDormAdministratorUrl,
      updateDormAdministratorDto,
      { headers: this.auth.getHeader() }
    );
  }

  deleteDorm(dormIdDto: DormId): Observable<void> {
    return this.http.post<void>(
      this.dromServiceUrl + this.deleteDormUrl,
      dormIdDto,
      { headers: this.auth.getHeader() }
    );
  }
}
