import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { GetFreeIntervalsDto } from '../interfaces/get-free-intervals-dto';
import { Observable } from 'rxjs';
import { FreeIntervalsDto } from '../interfaces/free-intervals-dto';
import { CreateLaundryAppointmentDto } from '../interfaces/create-laundry-appointment-dto';
import { LaundryAppointmentDto } from '../interfaces/laundry-appointment-dto';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private appointmentServiceUrl =
    'http://localhost:8080/api/laundryappointments';
  private createAppointmentUrl = '/create';
  private getFreeIntervalsUrl = '/get-free-intervals';
  private getWeeklyAppointmentsForDormForWashingMachineUrl =
    '/get-weekly-appointments-for-dorm-for-washing-machine';

  constructor(private http: HttpClient, private authService: AuthService) {}

  createAppointment(data: CreateLaundryAppointmentDto): Observable<void> {
    return this.http.post<void>(
      this.appointmentServiceUrl + this.createAppointmentUrl,
      data,
      { headers: this.authService.getHeader() }
    );
  }

  getFreeIntervalsForCreatingAppointment(
    getFreeIntervalsDto: GetFreeIntervalsDto
  ): Observable<FreeIntervalsDto> {
    return this.http.post<FreeIntervalsDto>(
      this.appointmentServiceUrl + this.getFreeIntervalsUrl,
      getFreeIntervalsDto,
      { headers: this.authService.getHeader() }
    );
  }

  getWeeklyAppointmentsForDormForWashingMachine(
    washingMachineId: string
  ): Observable<LaundryAppointmentDto[]> {
    let washingMachineIdDto = { washingMachineId: washingMachineId };
    return this.http.post<LaundryAppointmentDto[]>(
      this.appointmentServiceUrl +
        this.getWeeklyAppointmentsForDormForWashingMachineUrl,
      washingMachineIdDto,
      { headers: this.authService.getHeader() }
    );
  }
}
