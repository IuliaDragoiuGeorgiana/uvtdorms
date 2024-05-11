import { StatusMachine } from '../enums/status-machine';
import { LaundryAppointmentDto } from './laundry-appointment-dto';

export interface WashingMachine {
  id: string;
  name: string;
  isAvailable: boolean;
  associatedDryerId: string;
  weeklyAppointments: LaundryAppointmentDto[] | null;
  statusMachine: StatusMachine;
}
