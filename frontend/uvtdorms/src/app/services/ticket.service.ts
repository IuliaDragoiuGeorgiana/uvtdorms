import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTicketDto } from '../interfaces/create-ticket-dto';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private ticketServiceUrl = 'http://localhost:8080/api/tickets';
  private createTicketUrl = '/create';

  constructor(private http: HttpClient, private auth: AuthService) {}

  public createTicket(createTicketDto: CreateTicketDto): Observable<void> {
    return this.http.post<void>(
      this.ticketServiceUrl + this.createTicketUrl,
      createTicketDto,
      { headers: this.auth.getHeader() }
    );
  }
}
