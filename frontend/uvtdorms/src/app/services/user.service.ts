import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userServiceUrl = '/api/users';
  private getTestUserUrl = '/get-test-user';

  constructor(private http: HttpClient) { }

  getTestUser(): Observable<User>
  {
    return this.http.get<User>(this.userServiceUrl + this.getTestUserUrl);
  }
}
