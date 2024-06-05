import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { TicketDto } from '../../interfaces/ticket-dto';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ChangeStatusTicketDto } from '../../interfaces/change-status-ticket-dto';

@Component({
  selector: 'app-tickets-administration-page',
  templateUrl: './tickets-administration-page.component.html',
  styleUrl: './tickets-administration-page.component.css',
})
export class TicketsAdministrationPageComponent {
  public tickets: TicketDto[] = [];
  public changeStatusTicketDto : ChangeStatusTicketDto = {ticketId: ''};

  constructor(private ticketService: TicketService,private confirmationService: ConfirmationService, private messageService: MessageService) {
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
    switch (status) {
      case 'OPEN':
        return 'danger';
      case 'RESOLVED':
        return 'done';
      default:
        return 'info';
    }
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

  getButtonLabelTicketStatus(status: string): string {
    switch (status) {
      case 'OPEN':
        return 'OPEN';
      case 'RESOLVED':
        return 'RESOLVED';
      default:
        return 'Unknown Status';
    }
  }
  

  confirmChangeStatus(event: Event, ticket: TicketDto) {
    this.confirmationService.confirm({
      target: event.target!,
      message: 'Are you sure you want to change the status of this ticket?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.changeStatus(ticket);
        
      }
    });
  }
  

  changeStatus(ticketDto: TicketDto) {
    let changeStatusTicketDto: ChangeStatusTicketDto = {
      ticketId: ticketDto.id,
    };
    console.log(changeStatusTicketDto);
    this.ticketService.changeStatusTicket(changeStatusTicketDto).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'info',
          summary: 'Confirmed',
          detail: 'You have changed the status of the ticket'
        });
        window.location.reload();
        console.log('Ticket status changed');
      },
      error: (err) => {
        console.error(err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to change the status of the ticket'
        });
      }
    });
  }
  
}
