import { RegisterStudentDto } from "./register-student-dto";

export interface RegisterRequestDto extends RegisterStudentDto{
    requestDate: Date;
}
