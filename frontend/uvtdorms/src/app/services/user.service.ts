import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Role } from '../enums/role';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userServiceUrl = '/api/users';
  private userRole: Role | null = null;

  constructor(private http: HttpClient, private authService: AuthService) {}

  setRole(role: Role): void {
    this.userRole = role;
  }

  getRole(): Role | null {
    return this.userRole;
  }

  logout() {
    this.authService.setAuthToken(null);
    this.userRole = null;
  }
}
