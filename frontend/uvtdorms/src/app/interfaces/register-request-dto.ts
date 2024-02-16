import { RegisterRequestStatus } from '../enums/register-request-status';
import { RegisterStudentDto } from './register-student-dto';

export interface RegisterRequestDto extends RegisterStudentDto {
  createdOn: Date;
  status: RegisterRequestStatus;
}
