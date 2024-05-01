import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrl: './change-password-dialog.component.css',
})
export class ChangePasswordDialogComponent {
  hideNewPassword = true;
  hideConfirmPassword = true;
  changePasswordForm: FormGroup;

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<ChangePasswordDialogComponent>
  ) {
    this.changePasswordForm = new FormGroup(
      {
        newPassword: new FormControl('', [
          Validators.required,
          this.passwordStrengthValidator(),
        ]),
        confirmPassword: new FormControl('', [Validators.required, this.passwordsMatchValidator()]),
      }
    );
  }

  private passwordStrengthValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      const errors: ValidationErrors = {};

      if (!value) {
        return null;
      }

      if (value.length < 8) {
        errors['minLength'] = { requiredLength: 8, actualLength: value.length };
        return errors;
      }

      if (!/[A-Z]/.test(value)) {
        errors['uppercase'] = true;
        return errors;
      }

      if (!/[a-z]/.test(value)) {
        errors['lowercase'] = true;
        return errors;
      }

      if (!/\d/.test(value)) {
        errors['number'] = true;
        return errors;
      }

      return Object.keys(errors).length === 0 ? null : errors;
    };
  }

  private passwordsMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      const errors: ValidationErrors = {};

      if(!value) {
        return null;
      }

      if(value !== this.changePasswordForm.getRawValue().newPassword)
      {
        errors['mismatch'] = true;
        return errors;
      }

      return Object.keys(errors).length === 0 ? null : errors;
    }
  }

  onChangePassword() {
    if (this.changePasswordForm.invalid) return;
    this.userService.updatePasswoed(this.changePasswordForm.value).subscribe({
      next: (response) => {
        console.log(response);
        console.log('Password updated successfully');
        window.location.reload();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
