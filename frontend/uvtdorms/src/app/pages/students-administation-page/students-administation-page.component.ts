import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { RegisterRequestDto } from '../../interfaces/register-request-dto';
import { MatDialog } from '@angular/material/dialog';
import { RegisterRequestDetailsPopupComponent } from '../../elements/dialogs/studentsAdministration/register-request-details-popup/register-request-details-popup.component';
import { StudentDetailsDto } from '../../interfaces/student-details-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { RegisterRequestService } from '../../services/register-request.service';
import { StudentDetailsService } from '../../services/student-details.service';
import { EditRoomNumberDialogComponent } from '../../elements/dialogs/studentsAdministration/edit-room-number-dialog/edit-room-number-dialog.component';

@Component({
  selector: 'app-students-administation-page',
  templateUrl: './students-administation-page.component.html',
  styleUrl: './students-administation-page.component.css',
})
export class StudentsAdministationPageComponent implements AfterViewInit {
  registerRequests: RegisterRequestDto[] = [];
  studentsList: StudentDetailsDto[] = [];
  displayedColumns: string[] = [
    'First name',
    'Last name',
    'Email',
    'Dorm name',
    'Room number',
    'Matriculation number',
    'Phone number',
    'Edit',
    'Remove',
  ];
  dataSource = new MatTableDataSource<StudentDetailsDto>(this.studentsList);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(
    public dialog: MatDialog,
    private registerRequestService: RegisterRequestService,
    private studentDetailsService: StudentDetailsService
  ) {
    registerRequestService.getRegisterRequestsFromDrom().subscribe({
      next: (requests) => {
        console.log(requests);
        this.registerRequests = requests;
        console.log(this.registerRequests);
      },
      error: (error) => {
        console.log(error);
      },
    });

    studentDetailsService.getAllStudentsFromDorm().subscribe({
      next: (students) => {
        console.log(students);
        this.studentsList = students;
        this.dataSource = new MatTableDataSource<StudentDetailsDto>(
          this.studentsList
        );
        this.openEditRoomNumberDialog(students[0]);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  openRegisterRequestDialog(registerRequestDto: RegisterRequestDto): void {
    this.dialog.open(RegisterRequestDetailsPopupComponent, {
      data: registerRequestDto,
    });
  }
  openEditRoomNumberDialog (student: StudentDetailsDto): void {
    this.dialog.open(EditRoomNumberDialogComponent, {
      data: {
        studentEmail: student.email,
        dormName: student.dormName
      },
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    console.log(filterValue);
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
