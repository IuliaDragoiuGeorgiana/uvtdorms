export enum StatusMachine {
  FUNCTIONAL,
  BROKEN,
}

export function convertStringStatusMachineToEnum(
  value: string
): StatusMachine | undefined {
  const upperValue = value.toUpperCase();
  return StatusMachine[upperValue as keyof typeof StatusMachine];
}
