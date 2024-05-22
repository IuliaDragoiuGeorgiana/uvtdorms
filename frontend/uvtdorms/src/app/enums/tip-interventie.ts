export enum TipInterventie {
  INSTALATOR,
  TAMPLAR,
  ELECTRICIAN,
}

export function convertStringtipInterventieToEnum(
  value: string
): TipInterventie | undefined {
  const upperValue = value.toUpperCase();
  return TipInterventie[upperValue as keyof typeof TipInterventie];
}
