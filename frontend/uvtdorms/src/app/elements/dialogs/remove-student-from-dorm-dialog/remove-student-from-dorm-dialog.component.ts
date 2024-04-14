import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RemoveStudentDialogData } from '../../../interfaces/remove-student-dialog-data';
import { on } from 'events';
import { StudentDetailsService } from '../../../services/student-details.service';
import { User } from '../../../interfaces/user';

@Component({
  selector: 'app-remove-student-from-dorm-dialog',
  templateUrl: './remove-student-from-dorm-dialog.component.html',
  styleUrl: './remove-student-from-dorm-dialog.component.css'
})
export class RemoveStudentFromDormDialogComponent {
  constructor(
    private studentDetailsService: StudentDetailsService,
    public dialogRef: MatDialogRef<RemoveStudentFromDormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RemoveStudentDialogData
  ) {

  }
  onRemoveStudentFromRoom(): void {
    let  user: User = {
      email: this.data.studentEmail
    }
    this.studentDetailsService.deleteStudentFromDorm(user).subscribe({
      next: () => {
        console.log('email removed successfully');
        window.location.reload();
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

}