import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private appointmentServiceUrl = 'http://localhost:8080/api/laundryappointments';
  private createAppointmentUrl = '/create';
  private getFreeIntervalsUrl = '/get-free-intervals';

  constructor(private http: HttpClient, private authService: AuthService) { }

  createAppointment(data: {}) {
    return this.http.post(this.appointmentServiceUrl + this.createAppointmentUrl, data, { headers: this.authService.getHeader() });
  }

  getFreeIntervalsForCreatingAppointment(data: {}) {
    return this.http.post(this.appointmentServiceUrl + this.getFreeIntervalsUrl, data, { headers: this.authService.getHeader() });
  }
}
