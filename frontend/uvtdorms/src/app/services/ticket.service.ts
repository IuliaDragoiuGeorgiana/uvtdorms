import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTicketDto } from '../interfaces/create-ticket-dto';
import { AuthService } from './auth.service';
import { TicketDTO } from '../interfaces/ticket-dto';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private ticketServiceUrl = 'http://localhost:8080/api/tickets';
  private createTicketUrl = '/create';
  private getTicketsFromDormUrl = '/get-tickets-from-dorm';

  constructor(private http: HttpClient, private auth: AuthService) {}

  public createTicket(createTicketDto: CreateTicketDto): Observable<void> {
    return this.http.post<void>(
      this.ticketServiceUrl + this.createTicketUrl,
      createTicketDto,
      { headers: this.auth.getHeader() }
    );
  }

  public getTicketsFromDorm(): Observable<TicketDTO[]> {
    return this.http.get<TicketDTO[]>(
      this.ticketServiceUrl + this.getTicketsFromDormUrl,
      { headers: this.auth.getHeader() }
    );
  }
}
