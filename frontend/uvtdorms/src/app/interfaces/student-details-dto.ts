import { UserDetailsDto } from "./user-details-dto";

export interface StudentDetailsDto extends UserDetailsDto
{
    dormName: string;
    roomNumber: string;
    matriculationNumber: string;
}
