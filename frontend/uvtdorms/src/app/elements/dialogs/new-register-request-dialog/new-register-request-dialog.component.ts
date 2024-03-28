import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { RoomService } from '../../../services/room.service';
import { DormService } from '../../../services/dorm.service';
import { NewRegisterRequestDto } from '../../../interfaces/new-register-request-dto';
import { RegisterRequestService } from '../../../services/register-request.service';

@Component({
  selector: 'app-new-register-request-dialog',
  templateUrl: './new-register-request-dialog.component.html',
  styleUrl: './new-register-request-dialog.component.css',
})
export class NewRegisterRequestDialogComponent {
  private validRoomNumbers: string[] = [];
  public dormsNames: string[] = [];
  public newRequestForm = new FormGroup({
    dormName: new FormControl('', [Validators.required]),
    roomNumber: new FormControl('', [
      Validators.required,
      this.roomNumberValidator(),
    ]),
  });

  constructor(
    private roomService: RoomService,
    private dormService: DormService,
    private registerRequestService: RegisterRequestService
  ) {}

  ngOnInit() {
    this.dormService.getDormNames().subscribe({
      next: (dormsNames) => {
        this.dormsNames = dormsNames.names;
      },
    });
  }

  onDormSelected() {
    let selectedDormName: string = this.newRequestForm.get('dormName')?.value!;
    this.roomService.getRoomsNumbersFromDrom(selectedDormName).subscribe({
      next: (roomsNumbers) => {
        this.validRoomNumbers = roomsNumbers.numbers;
      },
    });
  }

  roomNumberValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const isValid = this.validRoomNumbers.includes(control.value.toString());
      return isValid ? null : { invalidRoomNumber: { value: control.value } };
    };
  }

  get roomNumber() {
    return this.newRequestForm.get('roomNumber');
  }

  sendNewRegisterRequest() {
    if (!this.newRequestForm.valid) {
      return;
    }
    console.log(this.newRequestForm.value);

    let newRegisterRequestDto: NewRegisterRequestDto = {
      dormName: this.newRequestForm.value.dormName!,
      roomNumber: this.newRequestForm.value.roomNumber!,
    };

    this.registerRequestService
      .createNewRegisterRequest(newRegisterRequestDto)
      .subscribe({
        next: () => {
          window.location.reload();
        },
        error(err) {
          console.error(err);
        },
      });
  }
}
