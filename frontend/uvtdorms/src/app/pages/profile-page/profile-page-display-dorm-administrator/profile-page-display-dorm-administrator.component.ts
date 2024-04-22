import { Component } from '@angular/core';
import { DisplayDormAdministratorDetailsDto } from '../../../interfaces/display-dorm-administrator-details-dto';
import { DormAdministratorDetailsService } from '../../../services/dorm-administrator-details.service';

@Component({
  selector: 'app-profile-page-display-dorm-administrator',
  templateUrl: './profile-page-display-dorm-administrator.component.html',
  styleUrl: './profile-page-display-dorm-administrator.component.css'
})
export class ProfilePageDisplayDormAdministratorComponent {

  public displayDormAdministratorDetails : DisplayDormAdministratorDetailsDto|undefined = undefined;

  constructor(private dormAdministrtorDetailsService : DormAdministratorDetailsService)
  {}

  ngOnInit() {
    this.dormAdministrtorDetailsService.getDormAdministratorDetails().subscribe({
      next: (dormAdministrtorDetails) => {
        this.displayDormAdministratorDetails = dormAdministrtorDetails;
      },
      error(err) {
        console.error(err);
      },
    });
  }
}
