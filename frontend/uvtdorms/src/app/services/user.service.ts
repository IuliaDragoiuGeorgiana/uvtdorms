import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Role } from '../enums/role';
import { Observable } from 'rxjs';
import { UserDetailsDto } from '../interfaces/user-details-dto';
import { EditPhoneNumberDto } from '../interfaces/edit-phone-number-dto';
import { ChangePasswordDto } from '../interfaces/change-password-dto';
import { ChangeProfilePictureDto } from '../interfaces/change-profile-picture-dto';
import { DisplayInactiveStudentDetails } from '../interfaces/display-inactive-student-details';
import { DormAdministratorDetailsService } from './dorm-administrator-details.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  private userServiceUrl = 'http://localhost:8080/api/users';
  private getUserDetailsUrl = '/get-user-details';
  private userRole: Role | null = null;
  private blockAccessToInactiveDormAdministrator: boolean = false;
  private updatePhoneNumberUrl = '/update-phone-number';
  private updatePasswordUrl = '/update-password';
  private updateProfilePictureUrl = '/update-profile-picture';
  private getInactiveStudentDetailsUrl = '/get-inactive-student-details';

  constructor(private http: HttpClient, private authService: AuthService, private dormAdministratorService: DormAdministratorDetailsService) { }

  setRole(role: Role): void {
    this.userRole = role;
    if (role === Role.ADMINISTRATOR) {
      this.dormAdministratorService.hasDormAdministratorAssociatedDorm().subscribe(
        (response) => {
          if (!response) {
            this.blockAccessToInactiveDormAdministrator = true;
          }
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  getRole(): Role | null {
    return this.userRole;
  }

  get isAccessBlockedToInactiveDormAdministrator(): boolean {
    return this.blockAccessToInactiveDormAdministrator;
  }

  logout() {
    this.authService.setAuthToken(null);
    this.userRole = null;
  }

  getUserDetails(): Observable<UserDetailsDto> {
    return this.http.get<UserDetailsDto>(
      this.userServiceUrl + this.getUserDetailsUrl,
      { headers: this.authService.getHeader() }
    );
  }

  getInactiveStudentDetails(): Observable<DisplayInactiveStudentDetails> {
    return this.http.get<DisplayInactiveStudentDetails>(
      this.userServiceUrl + this.getInactiveStudentDetailsUrl,
      { headers: this.authService.getHeader() }
    );
  }

  updatePhoneNumber(editPhoneNumberDto: EditPhoneNumberDto): Observable<void> {
    return this.http.post<void>(
      this.userServiceUrl + this.updatePhoneNumberUrl,
      editPhoneNumberDto,
      { headers: this.authService.getHeader() }
    );
  }

  updatePasswoed(changePasswordDto: ChangePasswordDto): Observable<void> {
    return this.http.post<void>(
      this.userServiceUrl + this.updatePasswordUrl,
      changePasswordDto,
      { headers: this.authService.getHeader() }
    );
  }

  updateProfilePicture(changeProfilePictureDto: ChangeProfilePictureDto): Observable<void> {
    return this.http.post<void>(
      this.userServiceUrl + this.updateProfilePictureUrl,
      changeProfilePictureDto,
      { headers: this.authService.getHeader() }
    );
  }

}
