import { RegisterRequestStatus } from '../enums/register-request-status';

export interface ListedRegisterRequestDto {
  createdOn: Date;
  dormitoryName: string;
  roomNumber: string;
  administratorContact: string;
  status: RegisterRequestStatus;
}
