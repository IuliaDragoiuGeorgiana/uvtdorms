import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { ModifiableLaundryAppointmentIdDto } from '../interfaces/modifiable-laundry-appointment-dto';

@Injectable({
  providedIn: 'root',
})
export class ModifiableLaundryAppointmentService {
  constructor(private http: HttpClient, private auth: AuthService) {}

  private modifiableLaundryAppointmentServiceUrl =
    'http://localhost:8080/api/modifiablelaundryappointments';
  private validateModifiableLaundryAppointmentUrl =
    '/validate-modifiable-laundry-appointment';
  private keepLaundryAppointmentWithoutDryerUrl =
    '/keep-laundry-appointment-without-dryer';
  private cancelLaundryAppointmentUrl = '/cancel-laundry-appointment';

  validateModifiableLaundryAppointment(id: string): Observable<any> {
    return this.http.get<any>(
      this.modifiableLaundryAppointmentServiceUrl +
        this.validateModifiableLaundryAppointmentUrl +
        '/' +
        id,
      { headers: this.auth.getHeader() }
    );
  }

  keepLaundryAppointmentWithoutDryer(
    modifiableLaundryAppointmentIdDto: ModifiableLaundryAppointmentIdDto
  ): Observable<void> {
    return this.http.post<void>(
      this.modifiableLaundryAppointmentServiceUrl +
        this.keepLaundryAppointmentWithoutDryerUrl,
      modifiableLaundryAppointmentIdDto,
      { headers: this.auth.getHeader() }
    );
  }

  cancelLaundryAppointment(
    modifiableLaundryAppointmentIdDto: ModifiableLaundryAppointmentIdDto
  ): Observable<void> {
    return this.http.post<void>(
      this.modifiableLaundryAppointmentServiceUrl + this.cancelLaundryAppointmentUrl,
      modifiableLaundryAppointmentIdDto,
      { headers: this.auth.getHeader() }
    );
  }
}
