import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Role } from '../../enums/role';
import { Router } from '@angular/router';
import { DetailedRoomDto } from '../../interfaces/detailed-room-dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { RoomService } from '../../services/room.service';
import { RoomNumberDto } from '../../interfaces/room-number-dto';
import { LightUserDto } from '../../interfaces/light-user-dto';
import { TranslateService } from '@ngx-translate/core';

interface expandedRoom {
  roomNumber: string;
  students: LightUserDto[];
}

@Component({
  selector: 'app-rooms-administration-page',
  templateUrl: './rooms-administration-page.component.html',
  styleUrl: './rooms-administration-page.component.css',
})
export class RoomsAdministrationPageComponent {
  public loading: boolean = false;
  private testRoomDto: DetailedRoomDto = {
    roomNumber: '123',
    numberOfStudents: 2,
  };

  public rooms: DetailedRoomDto[] = [];
  public isAddNewRoomDialogVisible: boolean = false;

  public addNewRoomForm: FormGroup = new FormGroup({
    roomNumber: new FormControl('', [Validators.required]),
  });

  public expandedRooms: expandedRoom[] = [];

  get hasRoomNumberError(): boolean {
    let control = this.addNewRoomForm.controls['roomNumber'];
    return control.touched && control.invalid;
  }

  get isMobileScreen(): boolean {
    return window.innerWidth <= 600;
  }

  constructor(
    private router: Router,
    private userSerivce: UserService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private roomService: RoomService,
    private translate: TranslateService
  ) {}

  private updateRooms(): void {
    this.loading = true;
    this.roomService.getRoomDetailsFromDorm().subscribe({
      next: (rooms) => {
        this.rooms = rooms;
        this.loading = false;
      },
    });
  }

  ngOnInit(): void {
    if (this.userSerivce.getRole() !== Role.ADMINISTRATOR) {
      this.router.navigate(['/not-found']);
    }

    this.updateRooms();
  }

  deleteRoom(room: DetailedRoomDto): void {
    this.confirmationService.confirm({
      message:
        this.translate.instant(
          'roomsAdministrationPage.deleteRoom.dialog.message'
        ) + ` ${room.roomNumber}?`,
      accept: () => {
        let roomNumberDto: RoomNumberDto = {
          roomNumber: room.roomNumber,
        };
        this.roomService.deleteRoomFromDorm(roomNumberDto).subscribe({
          next: () => {
            this.updateRooms();
            this.messageService.add({
              severity: 'success',
              summary: this.translate.instant(
                'roomsAdministrationPage.deleteRoom.dialog.success.summary'
              ),
              detail: this.translate.instant(
                'roomsAdministrationPage.deleteRoom.dialog.success.detail'
              ),
            });
          },
          error: (error) => {
            console.error('Error deleting room: ', error);
            this.messageService.add({
              severity: 'error',
              summary: this.translate.instant(
                'roomsAdministrationPage.deleteRoom.dialog.error.summary'
              ),
              detail: this.translate.instant(
                'roomsAdministrationPage.deleteRoom.dialog.error.detail'
              ),
            });
          },
        });
      },
    });
  }

  submitAddNewRoomForm(): void {
    this.addNewRoomForm.markAllAsTouched();
    this.addNewRoomForm.markAsDirty();
    this.addNewRoomForm.updateValueAndValidity();
    if (this.addNewRoomForm.invalid) {
      return;
    }

    let roomNumberDto: RoomNumberDto = {
      roomNumber: this.addNewRoomForm.value.roomNumber,
    };

    this.roomService.addRoomToDorm(roomNumberDto).subscribe({
      next: () => {
        this.updateRooms();
        this.isAddNewRoomDialogVisible = false;
        this.addNewRoomForm.reset();
        this.messageService.add({
          severity: 'success',
          summary: this.translate.instant(
            'roomsAdministrationPage.addNewRoomDialog.success.summary'
          ),
          detail: this.translate.instant(
            'roomsAdministrationPage.addNewRoomDialog.success.detail'
          ),
        });
      },
      error: (error) => {
        console.error('Error adding room: ', error);
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'roomsAdministrationPage.addNewRoomDialog.error.summary'
          ),
          detail: this.translate.instant(
            'roomsAdministrationPage.addNewRoomDialog.error.detail'
          ),
        });
      },
    });
  }

  public getStudentsFromRoom(room: DetailedRoomDto): LightUserDto[] {
    if (
      this.expandedRooms.some(
        (expandedRoom) => expandedRoom.roomNumber === room.roomNumber
      )
    ) {
      const expandedRoom = this.expandedRooms.find(
        (expandedRoom) => expandedRoom.roomNumber === room.roomNumber
      );
      return expandedRoom!.students!;
    }

    let roomNumberDto: RoomNumberDto = {
      roomNumber: room.roomNumber,
    };
    this.roomService.getStudentsFromRoom(roomNumberDto).subscribe({
      next: (students) => {
        let expandedRoom: expandedRoom = {
          roomNumber: room.roomNumber,
          students: students,
        };
        this.expandedRooms.push(expandedRoom);
        return expandedRoom.students;
      },
      error: (error) => {
        console.error('Error getting students from room: ', error);
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'roomsAdministrationPage.getStudentsFromRoom.error.summary'
          ),
          detail: this.translate.instant(
            'roomsAdministrationPage.getStudentsFromRoom.error.detail'
          ),
        });
        return [];
      },
    });

    return [];
  }
}
