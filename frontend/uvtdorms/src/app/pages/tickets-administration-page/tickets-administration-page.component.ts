import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { TicketDTO } from '../../interfaces/ticket-dto';

@Component({
  selector: 'app-tickets-administration-page',
  templateUrl: './tickets-administration-page.component.html',
  styleUrl: './tickets-administration-page.component.css',
})
export class TicketsAdministrationPageComponent {
  public tickets: TicketDTO[] = [];

  constructor(private ticketService: TicketService) {
    this.ticketService.getTicketsFromDorm().subscribe({
      next: (value) => {
        console.log(value);
        this.tickets = value;
      },
      error(error) {
        console.error(error);
      },
    });
  }

  public getStatusSeverity(status: string): string {
    if (status === 'OPEN') {
      return 'warning';
    }
    if (status === 'RESOLVED') {
      return 'success';
    }
    return 'info';
  }

  public formatDate(date: any[]): string {
    return (
      date[0] +
      '-' +
      (date[1] < 10 ? '0' + date[1] : date[1]) +
      '-' +
      (date[2] < 10 ? '0' + date[2] : date[2]) +
      ' ' +
      (date[3] < 10 ? '0' + date[3] : date[3]) +
      ':' +
      (date[4] < 10 ? '0' + date[4] : date[4])
    );
  }
}
