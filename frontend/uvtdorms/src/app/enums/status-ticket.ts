export enum StatusTicket {
  OPEN,
  RESOLVED,
}

export function convertStringStatusTicketToEnum(
  value: string
): StatusTicket | undefined {
  const upperValue = value.toUpperCase();
  return StatusTicket[upperValue as keyof typeof StatusTicket];
}
