import dayjs from 'dayjs';

export interface IBloodPressureSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldSistolicAverage?: number | null;
  fieldSistolicMax?: number | null;
  fieldSistolicMin?: number | null;
  fieldDiasatolicAverage?: number | null;
  fieldDiastolicMax?: number | null;
  fieldDiastolicMin?: number | null;
  bodyPosition?: number | null;
  measurementLocation?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBloodPressureSummary> = {};
