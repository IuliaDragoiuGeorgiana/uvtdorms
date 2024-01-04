import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private appointmentServiceUrl = 'localhost:8080/api/appointment';
  private createAppointmentUrl = '/create';

  constructor() { }


}
