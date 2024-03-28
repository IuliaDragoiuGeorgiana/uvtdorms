import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DormId } from '../interfaces/dorm-id';
import { AuthService } from './auth.service';
import { StudentDetailsDto } from '../interfaces/student-details-dto';
import { EditRoomDto } from '../interfaces/edit-room-dto';

@Injectable({
  providedIn: 'root',
})
export class StudentDetailsService {
  private studentsDetailsServiceUrl =
    'http://localhost:8080/api/studentdetails';
  private getStudentDormIdUrl = '/get-student-dorm-id';
  private getAllStudentsFromDormUrl = '/get-all-students-from-dorm';
  private updateRoomNumberUrl = '/update-room-number';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getStudentDormId(): Observable<DormId> {
    return this.http.get<DormId>(
      this.studentsDetailsServiceUrl + this.getStudentDormIdUrl,
      { headers: this.auth.getHeader() }
    );
  }
  getAllStudentsFromDorm(): Observable<StudentDetailsDto[]> {
    return this.http.get<StudentDetailsDto[]>(
      this.studentsDetailsServiceUrl + this.getAllStudentsFromDormUrl,
      { headers: this.auth.getHeader() }
    );
  }

  updateRoomNumber(editRoomDto: EditRoomDto): Observable<void> {
    return this.http.post<void>(
      this.studentsDetailsServiceUrl + this.updateRoomNumberUrl,
      editRoomDto,
      { headers: this.auth.getHeader() }
    );
  }
}
