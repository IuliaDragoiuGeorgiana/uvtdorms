import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DormId } from '../interfaces/dorm-id';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StudentDetailsService {

  private studentsDetailsServiceUrl = "http://localhost:8080/api/studentdetails";
  private getStudentDormIdUrl = "/get-student-dorm-id";

  constructor(private http: HttpClient, private auth: AuthService) { }

  getStudentDormId(): Observable<DormId> {
    return this.http.get<DormId>(this.studentsDetailsServiceUrl + this.getStudentDormIdUrl, { headers: this.auth.getHeader() });
  }
}
