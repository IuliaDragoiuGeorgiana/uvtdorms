import { Component } from '@angular/core';
import { User } from '../../../interfaces/user';
import { UserDetailsDto } from '../../../interfaces/user-details-dto';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-profile-page-display-application-administrator-details',
  templateUrl: './profile-page-display-application-administrator-details.component.html',
  styleUrl: './profile-page-display-application-administrator-details.component.css'
})
export class ProfilePageDisplayApplicationAdministratorDetailsComponent {

  public displayApplicationAdministratorDetails : UserDetailsDto|undefined = undefined;
  constructor(public user: UserService)
  {}

  ngOnInit(){
    this.user.getUserDetails().subscribe({
      next: (userDetails) => {
        this.displayApplicationAdministratorDetails = userDetails;
      },
      error(err) {
        console.error(err);
      },
    });
  }
}
