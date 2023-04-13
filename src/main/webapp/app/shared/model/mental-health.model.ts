import dayjs from 'dayjs';

export interface IMentalHealth {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  emotionDescription?: string | null;
  emotionValue?: number | null;
  startDate?: string | null;
  endDate?: string | null;
  mentalHealthScore?: number | null;
}

export const defaultValue: Readonly<IMentalHealth> = {};
