import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { TicketDto } from '../../interfaces/ticket-dto';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ChangeStatusTicketDto } from '../../interfaces/change-status-ticket-dto';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-tickets-administration-page',
  templateUrl: './tickets-administration-page.component.html',
  styleUrl: './tickets-administration-page.component.css',
})
export class TicketsAdministrationPageComponent {
  public tickets: TicketDto[] = [];
  public changeStatusTicketDto: ChangeStatusTicketDto = { ticketId: '' };

  constructor(
    private ticketService: TicketService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private translate: TranslateService
  ) {
    this.updateTickets();
  }

  private updateTickets(): void {
    this.ticketService.getTicketsFromDorm().subscribe({
      next: (value) => {
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
        return this.translate.instant('ticketsAdministrationPage.status.open');
      case 'RESOLVED':
        return this.translate.instant(
          'ticketsAdministrationPage.status.resolved'
        );
      default:
        return 'Unknown Status';
    }
  }

  confirmChangeStatus(event: Event, ticket: TicketDto) {
    this.confirmationService.confirm({
      target: event.target!,
      message: this.translate.instant(
        'ticketsAdministrationPage.changeStatus.dialog.message'
      ),
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.changeStatus(ticket);
      },
      acceptLabel: this.translate.instant(
        'ticketsAdministrationPage.changeStatus.dialog.buttons.yes'
      ),
      rejectLabel: this.translate.instant(
        'ticketsAdministrationPage.changeStatus.dialog.buttons.no'
      ),
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
          summary: this.translate.instant(
            'ticketsAdministrationPage.changeStatus.dialog.success.summary'
          ),
          detail: this.translate.instant(
            'ticketsAdministrationPage.changeStatus.dialog.success.detail'
          ),
        });
        this.updateTickets();
      },
      error: (err) => {
        console.error(err);
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'ticketsAdministrationPage.changeStatus.dialog.error.summary'
          ),
          detail: this.translate.instant(
            'ticketsAdministrationPage.changeStatus.dialog.error.detail'
          ),
        });
      },
    });
  }
}
