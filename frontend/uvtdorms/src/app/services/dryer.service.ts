import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Dryer } from '../interfaces/dryer';
import { AuthService } from './auth.service';
import { AvailableDryerDto } from '../interfaces/available-dryer-dto';

@Injectable({
  providedIn: 'root'
})
export class DryerService {

  private dryerServiceUrl = "http://localhost:8080/api/dryers";
  private getDryerFromDormUrl = "/get-dryer-from-dorm";
  private getAvailableDryerUrl = "/get-available-dryers";

  constructor(private http: HttpClient, private auth: AuthService) { }

  getDryerFromDorm(dormId: string): Observable<Dryer[]> {
    return this.http.get<Dryer[]>(this.dryerServiceUrl + this.getDryerFromDormUrl + "/" + dormId, { headers: this.auth.getHeader() });
  }

  getAvailableDryer(): Observable<AvailableDryerDto[]> {
    return this.http.get<AvailableDryerDto[]>(this.dryerServiceUrl + this.getAvailableDryerUrl, { headers: this.auth.getHeader() });
  }
}
