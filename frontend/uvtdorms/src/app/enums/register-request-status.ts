export enum RegisterRequestStatus {
  RECEIVED,
  ACCEPTED,
  DECLINED,
}

export function convertStringRequestStatusToEnum(
  value: string
): RegisterRequestStatus | undefined {
  const upperValue = value.toUpperCase();
  return RegisterRequestStatus[
    upperValue as keyof typeof RegisterRequestStatus
  ];
}
