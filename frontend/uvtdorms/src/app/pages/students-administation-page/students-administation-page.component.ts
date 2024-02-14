import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { RegisterRequestDto } from '../../interfaces/register-request-dto';
import { MatDialog } from '@angular/material/dialog';
import { RegisterRequestDetailsPopupComponent } from '../../elements/dialogs/studentsAdministration/register-request-details-popup/register-request-details-popup.component';
import { StudentDetailsDto } from '../../interfaces/student-details-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { RegisterRequestService } from '../../services/register-request.service';

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

  constructor(public dialog: MatDialog, private registerRequestService: RegisterRequestService) {
    registerRequestService.getRegisterRequestsFromDrom().subscribe({
      next: (requests) => {
        console.log(requests);
      },
      error: (error) => {
        console.log(error);
      }
    })
    for (let i = 0; i < 8; i++)
      this.registerRequests.push({
        firstName: 'Iulia',
        lastName: 'Dragoiu',
        email: 'iulia.dragoiu02@e-uvt.ro',
        dormName: 'C13',
        roomNumber: '127',
        matriculationNumber: 'I3183',
        phoneNumber: '0729616799',
        requestDate: new Date(),
      });

    for (let i = 0; i < 80; i++)
      this.studentsList.push({
        firstName: 'Iulia' + i,
        lastName: 'Dragoiu',
        email: 'iulia.dragoiu02@e-uvt.ro',
        dormName: 'C13',
        roomNumber: '127' + i,
        matriculationNumber: 'I3183',
        phoneNumber: '0729616799',
      });
  }

  openRegisterRequestDialog(registerRequestDto: RegisterRequestDto): void {
    this.dialog.open(RegisterRequestDetailsPopupComponent, {
      data: registerRequestDto,
    });
  }

  applyFilter(event: Event) : void
  {
    const filterValue = (event.target as HTMLInputElement).value;
    console.log(filterValue);
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
