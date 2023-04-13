import dayjs from 'dayjs';

export interface ITemperatureSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldAverage?: number | null;
  fieldMax?: number | null;
  fieldMin?: number | null;
  measurementLocation?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ITemperatureSummary> = {};
