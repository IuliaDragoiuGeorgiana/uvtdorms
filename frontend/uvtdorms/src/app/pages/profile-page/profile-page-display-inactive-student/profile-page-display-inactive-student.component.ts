import { Component } from '@angular/core';
import { DisplayInactiveStudentDetails } from '../../../interfaces/display-inactive-student-details';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-profile-page-display-inactive-student',
  templateUrl: './profile-page-display-inactive-student.component.html',
  styleUrl: './profile-page-display-inactive-student.component.css'
})
export class ProfilePageDisplayInactiveStudentComponent {

  public displayInactiveStudentDetails : DisplayInactiveStudentDetails|undefined = undefined;
  constructor(private userService : UserService)
  {}

  ngOnInit() {
    this.userService.getInactiveStudentDetails().subscribe({
      next: (inactiveStudentDetails) => {
        this.displayInactiveStudentDetails = inactiveStudentDetails;
      },
      error(err) {
        console.error(err);
      },
    });
  }
}
