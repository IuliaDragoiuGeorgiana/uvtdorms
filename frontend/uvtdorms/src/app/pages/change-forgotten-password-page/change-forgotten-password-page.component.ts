import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { PasswordResetService } from '../../services/password-reset.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ResetPasswordDto } from '../../interfaces/reset-password-dto';

@Component({
  selector: 'app-change-forgotten-password-page',
  templateUrl: './change-forgotten-password-page.component.html',
  styleUrl: './change-forgotten-password-page.component.css',
})
export class ChangeForgottenPasswordPageComponent {
  private token: string = '';
  private isValidationOngoing: boolean = false;
  private isResetPasswordLoading: boolean = false;
  public isTokenValid: boolean = false;

  public resetPasswordForm = new FormGroup({
    newPassword: new FormControl('', [
      Validators.required,
      this.passwordStrengthValidator(),
    ]),
    confirmPassword: new FormControl('', [
      Validators.required,
      this.passwordsMatchValidator(),
    ]),
  });

  constructor(
    private passwordResetService: PasswordResetService,
    private route: ActivatedRoute,
    private router: Router,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token')!;
    console.log('Token: ', this.token);

    this.isValidationOngoing = true;
    let resetPasswordTokenDto = {
      token: this.token,
    };
    this.passwordResetService.validateToken(resetPasswordTokenDto).subscribe({
      next: () => {
        this.isTokenValid = true;
      },
      error: () => {
        this.isValidationOngoing = false;
        this.confirmationService.confirm({
          message: 'The token is invalid. Please try again.',
          header: 'Invalid token',
          icon: 'pi pi-exclamation-triangle',
          acceptIcon: 'none',
          rejectIcon: 'none',
          acceptLabel: 'OK',
          rejectVisible: false,
          rejectButtonStyleClass: 'p-button-text',
          accept: () => {
            this.router.navigate(['/login']);
          },
        });
      },
      complete: () => {
        this.isValidationOngoing = false;
      },
    });
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

      if (!value) {
        return null;
      }

      if (value !== this.resetPasswordForm.getRawValue().newPassword) {
        errors['mismatch'] = true;
        return errors;
      }

      return Object.keys(errors).length === 0 ? null : errors;
    };
  }

  get arePasswordsMatching(): boolean {
    return (
      this.resetPasswordForm.errors && this.resetPasswordForm.errors['mismatch']
    );
  }

  get isLoadingScreenVisible(): boolean {
    return this.isValidationOngoing || this.isResetPasswordLoading;
  }

  resetPassword(): void {
    console.log(this.resetPasswordForm.value);
    if (this.resetPasswordForm.invalid) {
      this.resetPasswordForm.markAllAsTouched();
      return;
    }

    let resetPasswordDto: ResetPasswordDto = {
      token: this.token,
      newPassword: this.resetPasswordForm.getRawValue().newPassword!,
    };

    this.isResetPasswordLoading = true;
    this.passwordResetService.resetPassword(resetPasswordDto).subscribe({
      next: () => {
        this.isResetPasswordLoading = false;
        this.confirmationService.confirm({
          message: 'Your password has been successfully reset.',
          header: 'Password reset',
          icon: 'pi pi-check',
          acceptIcon: 'none',
          rejectIcon: 'none',
          acceptLabel: 'OK',
          rejectVisible: false,
          rejectButtonStyleClass: 'p-button-text',
          accept: () => {
            this.router.navigate(['/login']);
          },
        });
      },
      error: () => {
        this.isResetPasswordLoading = false;
        this.confirmationService.confirm({
          message: 'An error occurred. Please try again.',
          header: 'Error',
          icon: 'pi pi-exclamation-triangle',
          acceptIcon: 'none',
          rejectIcon: 'none',
          acceptLabel: 'OK',
          rejectVisible: false,
          rejectButtonStyleClass: 'p-button-text',
        });
      },
    });
  }
}
