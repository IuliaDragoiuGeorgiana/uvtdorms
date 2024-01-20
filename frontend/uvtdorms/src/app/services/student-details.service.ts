import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DormId } from '../interfaces/dorm-id';

@Injectable({
  providedIn: 'root'
})
export class StudentDetailsService {

  private studentsDetailsServiceUrl="http://localhost:8080/api/studentdetails";
  private getStudentDormIdUrl="/get-student-dorm-id";

  constructor(private http: HttpClient) { }

  getStudentDormId(email:String):Observable<DormId>{
    return this.http.get<DormId>(this.studentsDetailsServiceUrl+this.getStudentDormIdUrl+'/'+email );
  }
}
