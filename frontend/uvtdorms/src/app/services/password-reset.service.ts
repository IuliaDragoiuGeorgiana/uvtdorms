import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EmailDto } from '../interfaces/email-dto';
import { Observable } from 'rxjs';
import { ResetPasswordTokenDto } from '../interfaces/reset-password-token-dto';
import { ResetPasswordDto } from '../interfaces/reset-password-dto';

@Injectable({
  providedIn: 'root',
})
export class PasswordResetService {
  private passwordResetServiceUrl = 'http://localhost:8080/api/password-reset';
  private forgotPasswordUrl = '/forgot-password';
  private resetPasswordUrl = '/reset-password';
  private validateTokenUrl = '/validate-token';

  constructor(private http: HttpClient) {}

  public forgotPassword(emailDto: EmailDto): Observable<void> {
    return this.http.post<void>(
      this.passwordResetServiceUrl + this.forgotPasswordUrl,
      emailDto
    );
  }

  public validateToken(
    resetPasswordTokenDto: ResetPasswordTokenDto
  ): Observable<void> {
    return this.http.post<void>(
      this.passwordResetServiceUrl + this.validateTokenUrl,
      resetPasswordTokenDto
    );
  }

  public resetPassword(resetPasswordDto: ResetPasswordDto): Observable<void> {
    return this.http.post<void>(
      this.passwordResetServiceUrl + this.resetPasswordUrl,
      resetPasswordDto
    );
  }
}
