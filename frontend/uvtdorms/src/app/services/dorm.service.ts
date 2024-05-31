import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DormNamesDto } from '../interfaces/dorm-names';
import { DormDto } from '../interfaces/dorm-dto';

@Injectable({
  providedIn: 'root',
})
export class DormService {
  private dromServiceUrl = 'http://localhost:8080/api/dorms';
  private getDormNamesUrl = '/dorms-names';
  private getAllDormsUrl = '/all-dorms';
  private addDormUrl = '/add-dorm';

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
}
