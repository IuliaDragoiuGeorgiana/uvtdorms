import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Role } from '../../enums/role';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  loginPagePath: string = '/login';
  laundryAppointmentsPath = '/laundry-appointments';
  public isSideBarVisible = false;

  constructor(private router: Router, private userSevice: UserService) {}

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  role(): Role | null {
    return this.userSevice.getRole();
  }

  isStudent(): boolean {
    return this.role() === Role.STUDENT;
  }

  isDormAdmistrator(): boolean {
    return this.role() === Role.ADMINISTRATOR;
  }

  isAppAdministrator(): boolean {
    return this.role() === Role.APPLICATION_ADMINISTRATOR;
  }

  logout() {
    this.userSevice.logout();
    this.router.navigate([this.loginPagePath]);
  }
}
