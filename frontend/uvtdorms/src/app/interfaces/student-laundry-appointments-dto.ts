import { StatusLaundry } from '../enums/status-laundry';

export interface StudentLaundryAppointmentsDto {
  statusLaundry: StatusLaundry;
  washingMachineNumber: String;
  dryerNumber: String;
  intervalBeginDate: Date;
}
