import dayjs from 'dayjs';

export interface IBloodGlucoseSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldAverage?: number | null;
  fieldMax?: number | null;
  fieldMin?: number | null;
  intervalFood?: number | null;
  mealType?: string | null;
  relationTemporalSleep?: number | null;
  sampleSource?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBloodGlucoseSummary> = {};
