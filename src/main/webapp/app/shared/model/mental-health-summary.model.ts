import dayjs from 'dayjs';

export interface IMentalHealthSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  emotionDescripMain?: string | null;
  emotionDescripSecond?: string | null;
  fieldMentalHealthAverage?: number | null;
  fieldMentalHealthMax?: number | null;
  fieldMentalHealthMin?: number | null;
  scoreMentalRisk?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IMentalHealthSummary> = {};
