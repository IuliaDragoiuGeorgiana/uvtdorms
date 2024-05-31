export enum Role {
  STUDENT,
  ADMINISTRATOR,
  APPLICATION_ADMINISTRATOR,
  INACTIV_STUDENT,
}

export function convertStringRoleToEnum(value: string): Role | undefined {
  const upperValue = value.toUpperCase();
  return Role[upperValue as keyof typeof Role];
}
