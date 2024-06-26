import { StatusTicket } from '../enums/status-ticket';
import { TipInterventie } from '../enums/tip-interventie';

export interface TicketDto {
  id: string;
  creationDate: string[];
  statusTicket: StatusTicket;
  tipInterventie: TipInterventie;
  title: string;
  description: string;
  alreadyAnuncement: boolean;
  studentEmail: string;
  roomNumber: string;
}
