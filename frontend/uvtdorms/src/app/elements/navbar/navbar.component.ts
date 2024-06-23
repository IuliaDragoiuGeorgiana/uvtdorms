import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Role } from '../../enums/role';
import { TranslateService } from '@ngx-translate/core';

enum Language {
  en = 'en',
  ro = 'ro',
}

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  loginPagePath: string = '/login';
  laundryAppointmentsPath = '/laundry-appointments';
  public isSideBarVisible = false;
  private currentLanguage: Language = Language.ro;

  constructor(
    private router: Router,
    private userSevice: UserService,
    private translate: TranslateService
  ) {}

  changeLanguage(): void {
    if (this.currentLanguage === Language.en) {
      this.translate.use('ro');
      this.currentLanguage = Language.ro;
    } else {
      this.translate.use('en');
      this.currentLanguage = Language.en;
    }
  }

  get isRoSelected(): boolean {
    return this.currentLanguage === Language.ro;
  }

  get isEnSelected(): boolean {
    return this.currentLanguage === Language.en;
  }

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
    return this.role() === Role.ADMINISTRATOR && !this.isInactiveDormAdministrator;
  }

  isAppAdministrator(): boolean {
    return this.role() === Role.APPLICATION_ADMINISTRATOR;
  }

  get isInactiveDormAdministrator(): boolean {
    return this.userSevice.isAccessBlockedToInactiveDormAdministrator;
  }

  logout() {
    this.userSevice.logout();
    this.router.navigate([this.loginPagePath]);
  }
}
