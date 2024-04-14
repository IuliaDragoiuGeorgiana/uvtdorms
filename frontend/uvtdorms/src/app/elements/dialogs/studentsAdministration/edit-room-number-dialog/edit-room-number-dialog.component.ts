import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RoomService } from '../../../../services/room.service';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { EditRoomDialogDto } from '../../../../interfaces/edit-room-dialog-dto';
import { EditRoomDto } from '../../../../interfaces/edit-room-dto';
import { StudentDetailsService } from '../../../../services/student-details.service';

@Component({
  selector: 'app-edit-room-number-dialog',
  templateUrl: './edit-room-number-dialog.component.html',
  styleUrl: './edit-room-number-dialog.component.css',
})
export class EditRoomNumberDialogComponent {
  private validRoomNumbers: string[] = [];
  editRoomNumberForm = new FormGroup({
    roomNumber: new FormControl('', [
      Validators.required,
      this.roomNumberValidator(),
    ]),
  });

  constructor(
    public dialogRef: MatDialogRef<EditRoomNumberDialogComponent>,
    private roomService: RoomService,
    private studentDetailsService: StudentDetailsService,
    @Inject(MAT_DIALOG_DATA) public data: EditRoomDialogDto
  ) {
    roomService.getRoomsNumbersFromDrom(data.dormName).subscribe({
      next: (roomNumbers) => {
        console.log(roomNumbers);
        this.validRoomNumbers = roomNumbers.numbers;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  roomNumberValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const isValid = this.validRoomNumbers.includes(control.value.toString());
      return isValid ? null : { invalidRoomNumber: { value: control.value } };
    };
  }

  onEditRoomNumber(): void {
    if (this.editRoomNumberForm.invalid) return;
    console.log(this.editRoomNumberForm);
    let editRoomDto: EditRoomDto = {
      studentEmail: this.data.studentEmail,
      roomNumber: this.roomNumber?.value!,
    };
    this.studentDetailsService.updateRoomNumber(editRoomDto).subscribe({
      next: () => {
        console.log('room number updated');
        window.location.reload();
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  get roomNumber() {
    return this.editRoomNumberForm.get('roomNumber');
  }
}
