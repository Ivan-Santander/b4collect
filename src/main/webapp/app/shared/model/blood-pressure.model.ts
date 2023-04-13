import dayjs from 'dayjs';

export interface IBloodPressure {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldBloodPressureSystolic?: number | null;
  fieldBloodPressureDiastolic?: number | null;
  fieldBodyPosition?: string | null;
  fieldBloodPressureMeasureLocation?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBloodPressure> = {};
