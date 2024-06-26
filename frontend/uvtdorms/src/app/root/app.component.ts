import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { UserService } from '../services/user.service';
import { convertStringRoleToEnum } from '../enums/role';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  public title: string = 'uvtdorms';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private translate: TranslateService
  ) {
    translate.setDefaultLang('ro');
    translate.use('ro');
  }

  ngOnInit() {
    let initialPath =
      location.pathname != '/' ? location.pathname : '/not-found';
    let initialQuery = location.search;
    if (initialQuery != '' && initialPath != '/not-found') {
      initialPath += initialQuery;
    }

    if (this.authService.getAuthToken() != null) {
      this.authService.loginWithToken().subscribe({
        next: (tokenDto) => {
          this.userService.setRole(convertStringRoleToEnum(tokenDto.role)!);
          this.authService.setAuthToken(tokenDto.token);
          this.router.navigate([initialPath]).catch(() => {
            this.router.navigate(['/not-found']);
          });
        },
        error: () => {
          if (
            !initialPath.startsWith('/reset-password') &&
            !initialPath.startsWith('/forgot-password')
          ) {
            this.router.navigate(['/login']);
          }
        },
      });
    } else {
      if (
        !initialPath.startsWith('/reset-password') &&
        !initialPath.startsWith('/forgot-password')
      ) {
        this.router.navigate(['/login']);
      }
    }
  }
}
