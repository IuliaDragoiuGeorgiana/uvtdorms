import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DormNamesDto } from '../interfaces/dorm-names';

@Injectable({
  providedIn: 'root',
})
export class DormService {
  private dromServiceUrl = 'http://localhost:8080/api/dorms';
  private getDormNamesUrl = '/dorms-names';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getDormNames(): Observable<DormNamesDto> {
    return this.http.get<DormNamesDto>(
      this.dromServiceUrl + this.getDormNamesUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
