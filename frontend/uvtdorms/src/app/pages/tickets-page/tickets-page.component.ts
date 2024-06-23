import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TicketService } from '../../services/ticket.service';
import { CreateTicketDto } from '../../interfaces/create-ticket-dto';
import {
  TipInterventie,
  convertStringtipInterventieToEnum,
} from '../../enums/tip-interventie';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-tickets-page',
  templateUrl: './tickets-page.component.html',
  styleUrl: './tickets-page.component.css',
})
export class TicketsPageComponent {
  public categories: String[] = [];
  public loadigScreen: boolean = false;

  ticketForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    tipInterventie: new FormControl('', Validators.required),
    reportedBefore: new FormControl(false, Validators.required),
  });

  constructor(
    private ticketService: TicketService,
    private messageService: MessageService,
    private translate: TranslateService
  ) {
    let interventionTypes = Object.keys(TipInterventie);
    for (let i = 0; i < interventionTypes.length; i++) {
      if (isNaN(Number(interventionTypes[i]))) {
        this.categories.push(interventionTypes[i]);
      }
    }
  }

  get title() {
    return this.ticketForm.get('title');
  }

  get description() {
    return this.ticketForm.get('description');
  }

  onSubmitTicket() {
    this.ticketForm.markAllAsTouched();
    if (this.ticketForm.invalid) {
      return;
    }
    const createTicketDto: CreateTicketDto = {
      tipInterventie: convertStringtipInterventieToEnum(
        this.ticketForm.value.tipInterventie!
      )!,
      title: this.ticketForm.value.title!,
      description: this.ticketForm.value.description!,
      alreadyAnuncement: this.ticketForm.value.reportedBefore!,
    };
    this.loadigScreen = true;
    this.ticketService.createTicket(createTicketDto).subscribe({
      next: () => {
        this.displayConfirmMessage();
        this.ticketForm.reset();
        this.loadigScreen = false;
      },
      error: () => {
        this.displayErrorMessage();
        this.loadigScreen = false;
      },
    });
  }

  private displayErrorMessage(): void {
    this.messageService.add({
      severity: 'error',
      summary: this.translate.instant('ticketsPage.display.error.summary'),
      detail: this.translate.instant('ticketsPage.display.error.detail'),
      sticky: true,
    });
  }

  private displayConfirmMessage(): void {
    this.messageService.add({
      severity: 'success',
      summary: this.translate.instant('ticketsPage.display.success.summary'),
      detail: this.translate.instant('ticketsPage.display.success.detail'),
      life: 3000,
    });
  }
}
