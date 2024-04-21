import { Component } from '@angular/core';
import { StudentDetailsDto } from '../../../interfaces/student-details-dto';
import { StudentDetailsService } from '../../../services/student-details.service';
import { DisplayStudentsDetailsDto } from '../../../interfaces/display-students-details-dto';

@Component({
  selector: 'app-profile-page-display-student-details',
  templateUrl: './profile-page-display-student-details.component.html',
  styleUrl: './profile-page-display-student-details.component.css'
})
export class ProfilePageDisplayStudentDetailsComponent {

  public displayStudentDetails: DisplayStudentsDetailsDto|undefined = undefined;

  constructor(private studentDetailsService: StudentDetailsService)
  {}

  ngOnInit() {
    this.studentDetailsService.getStudentDetails().subscribe({
      next: (studentDetails) => {
        this.displayStudentDetails = studentDetails;
        console.log(studentDetails);
      },
      error(err) {
        console.error(err);
      },
    });
  }


}
