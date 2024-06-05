import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTicketDto } from '../interfaces/create-ticket-dto';
import { AuthService } from './auth.service';
import { TicketDto } from '../interfaces/ticket-dto';
import { StudentTicketsDto } from '../interfaces/student-tickets-dto';
import { ChangeStatusTicketDto } from '../interfaces/change-status-ticket-dto';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private ticketServiceUrl = 'http://localhost:8080/api/tickets';
  private createTicketUrl = '/create';
  private getTicketsFromDormUrl = '/get-tickets-from-dorm';
  private getStudentTicketsUrl = '/get-student-tickets';
  private changeStatusTicketUrl = '/update-ticket-status';

  constructor(private http: HttpClient, private auth: AuthService) {}

  public createTicket(createTicketDto: CreateTicketDto): Observable<void> {
    return this.http.post<void>(
      this.ticketServiceUrl + this.createTicketUrl,
      createTicketDto,
      { headers: this.auth.getHeader() }
    );
  }

  public getTicketsFromDorm(): Observable<TicketDto[]> {
    return this.http.get<TicketDto[]>(
      this.ticketServiceUrl + this.getTicketsFromDormUrl,
      { headers: this.auth.getHeader() }
    );
  }

  public getStudentTickets(): Observable<StudentTicketsDto[]> {
    return this.http.get<StudentTicketsDto[]>(
      this.ticketServiceUrl + this.getStudentTicketsUrl,
      { headers: this.auth.getHeader() }
    );
  }

  public changeStatusTicket(changeStatusTicket:ChangeStatusTicketDto): Observable<ChangeStatusTicketDto> {
    return this.http.post<ChangeStatusTicketDto>(
      this.ticketServiceUrl + this.changeStatusTicketUrl,
       changeStatusTicket,
      { headers: this.auth.getHeader() }
    );
  }
}
