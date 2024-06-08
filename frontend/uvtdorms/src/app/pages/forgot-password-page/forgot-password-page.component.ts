import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PasswordResetService } from '../../services/password-reset.service';
import { EmailDto } from '../../interfaces/email-dto';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrl: './forgot-password-page.component.css',
})
export class ForgotPasswordPageComponent {
  public isLoadingScreenVisible = false;
  public emailForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  constructor(
    private passwordResetService: PasswordResetService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private translate: TranslateService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public submitForgotPasswordForm(): void {
    if (this.emailForm.invalid) {
      return;
    }

    this.isLoadingScreenVisible = true;
    let emailDto = this.emailForm.value as EmailDto;
    this.passwordResetService.forgotPassword(emailDto).subscribe({
      next: () => {
        this.confirmationService.confirm({
          header: this.translate.instant('forgotPassword.submitConfirm.success.messageheader'),
          message:this.translate.instant('forgotPassword.submitConfirm.success.message') ,
          acceptLabel: 'Ok',
          rejectVisible: false,
          accept: () => {
            this.emailForm.reset();
            this.router.navigate(['/login']);
          },
        });
      },
      error: (err) => {
        console.log(err);
        this.isLoadingScreenVisible = false;
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant('forgotPassword.submitConfirm.error.header'),
          detail: err.error.message ? err.error.message : this.translate.instant('forgotPassword.submitConfirm.error.message'),
          life: 5000,
        });
      },
      complete: () => {
        this.isLoadingScreenVisible = false;
      },
    });
  }
}
