import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { EditPhoneNumberDto } from '../../../interfaces/edit-phone-number-dto';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-phone-number-dialog',
  templateUrl: './edit-phone-number-dialog.component.html',
  styleUrl: './edit-phone-number-dialog.component.css',
})
export class EditPhoneNumberDialogComponent {
  editPhoneNumberForm = new FormGroup({
    phoneNumber: new FormControl('', [
      Validators.required,
      Validators.pattern('[0-9]*'),
      Validators.minLength(10),
      this.phoneNumberAlreadyExists(),
    ]),
  });
  isPhoneNumberAlreadyExists: boolean = false;

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<EditPhoneNumberDialogComponent>
  ) {}

  onEditPhoneNumber() {
    if (this.editPhoneNumberForm.invalid) return;
    let editPhoneNumberDto: EditPhoneNumberDto = {
      phoneNumber: this.phoneNumber?.value!,
    };
    this.isPhoneNumberAlreadyExists = false;
    this.editPhoneNumberForm.get('phoneNumber')?.updateValueAndValidity();
    this.userService.updatePhoneNumber(editPhoneNumberDto).subscribe({
      next: (response) => {
        console.log(response);
        window.location.reload();
      },
      error: (error) => {
        if (error.error.message === 'Phone number already in use!') {
          this.isPhoneNumberAlreadyExists = true;
          this.editPhoneNumberForm.get('phoneNumber')?.updateValueAndValidity();
        }
        console.log(error);
      },
    });
  }

  phoneNumberAlreadyExists(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      console.info('Hellooo');
      if (this.isPhoneNumberAlreadyExists) {
        return { phoneNumberExists: true };
      }
      return null;
    };
  }

  get phoneNumber() {
    return this.editPhoneNumberForm.get('phoneNumber');
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
