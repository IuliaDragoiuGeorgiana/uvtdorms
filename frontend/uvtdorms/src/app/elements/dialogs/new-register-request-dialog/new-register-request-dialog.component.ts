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
import { ConfirmationService, MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-new-register-request-dialog',
  templateUrl: './new-register-request-dialog.component.html',
  styleUrl: './new-register-request-dialog.component.css',
})
export class NewRegisterRequestDialogComponent {
  public isLoadingScreenVisible: boolean = false;
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
    private registerRequestService: RegisterRequestService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private translate: TranslateService
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
    if (this.newRequestForm.invalid) {
      return;
    }
    let newRegisterRequestDto: NewRegisterRequestDto = {
      dormName: this.newRequestForm.value.dormName!,
      roomNumber: this.newRequestForm.value.roomNumber!,
    };
    this.isLoadingScreenVisible = true;
    this.registerRequestService
      .createNewRegisterRequest(newRegisterRequestDto)
      .subscribe({
        next: () => {
          this.displayConfirmMessage();
          window.location.reload();
        },
        error: (error) => {
          console.error(error);
          this.isLoadingScreenVisible = false;
          this.displayErrorMessage(error);
        },
      });
  }

  private displayErrorMessage(error: any): void {
    let errorMsg: string = error.error.message;
    if (errorMsg === 'You already have a pending register request.') {
      errorMsg = this.translate.instant(
        'dialog.newRegisterRequest.errorAlreadyExists'
      );
    }
    this.messageService.add({
      severity: 'error',
      summary: this.translate.instant('dialog.newRegisterRequest.errorDialog'),
      detail: errorMsg,
      sticky: true,
    });
  }

  private displayConfirmMessage(): void {
    this.messageService.add({
      severity: 'success',
      summary: 'Confirmed',
      detail: this.translate.instant('dialog.newRegisterRequest.successDialog'),
      life: 3000,
    });
  }
}
