export interface EvenimentDto {
  id: string;
  title: string;
  description: string;
  dormAdministratorEmail: string;
  dormAdministratorName: string;
  canPeopleAttend: boolean;
  isUserAttending: boolean;
  numberOfAttendees: number;
  startDate: string[];
}
