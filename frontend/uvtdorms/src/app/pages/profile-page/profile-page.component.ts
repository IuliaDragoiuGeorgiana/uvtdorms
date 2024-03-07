import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserDetailsDto } from '../../interfaces/user-details-dto';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
})
export class ProfilePageComponent {
  public user: UserDetailsDto | undefined = undefined;

  constructor(private userService: UserService) {}
  ngOnInit() {
    this.userService.getUserDetails().subscribe({
      next: (userDetails) => {
        this.user = userDetails;
      },
      error(err) {
        console.error(err);
      },
    });
  }
}
