import dayjs from 'dayjs';

export interface IBloodGlucose {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldBloodGlucoseLevel?: number | null;
  fieldTemporalRelationToMeal?: number | null;
  fieldMealType?: number | null;
  fieldTemporalRelationToSleep?: number | null;
  fieldBloodGlucoseSpecimenSource?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBloodGlucose> = {};
