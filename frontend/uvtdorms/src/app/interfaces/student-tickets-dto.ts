import { StatusTicket } from "../enums/status-ticket";
import { TipInterventie } from "../enums/tip-interventie";

export interface StudentTicketsDto {
    creationDate: Date;
    statusTicket: StatusTicket;
    tipInterventie: TipInterventie;
    title:String;
    description:String;
    alreadyAnuncement: boolean;
}