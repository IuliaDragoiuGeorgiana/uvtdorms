import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { UserService } from '../services/user.service';
import { convertStringRoleToEnum } from '../enums/role';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'uvtdorms';

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit() {
    if (this.authService.getAuthToken() != null) {
      this.authService.loginWithToken().subscribe({
        next: (tokenDto) => {
          this.userService.setRole(convertStringRoleToEnum(tokenDto.role)!);
          this.authService.setAuthToken(tokenDto.token);
        },
      });
    }
  }
}
