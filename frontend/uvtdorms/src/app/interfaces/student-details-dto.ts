import { UserDetailsWithoutPictureDto } from "./user-details-without-picture-dto";

export interface StudentDetailsDto extends UserDetailsWithoutPictureDto
{
    dormName: string;
    roomNumber: string;
    matriculationNumber: string;
}
