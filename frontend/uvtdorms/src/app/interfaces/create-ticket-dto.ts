import { StatusTicket } from '../enums/status-ticket';
import { TipInterventie } from '../enums/tip-interventie';

export interface CreateTicketDto {
  tipInterventie: TipInterventie;
  title: String;
  description: String;
  alreadyAnuncement: Boolean;
}
