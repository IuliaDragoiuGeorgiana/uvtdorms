import { StatusMachine } from '../enums/status-machine';
import { LaundryAppointmentDto } from './laundry-appointment-dto';

export interface Dryer {
  id: string;
  name: string;
  isAvailable: boolean;
  associatedWashingMachineId: string;
  weeklyAppointments: LaundryAppointmentDto[] | null;
  statusMachine: StatusMachine;
}
