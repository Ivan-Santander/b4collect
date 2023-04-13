import dayjs from 'dayjs';

export interface IPoweSample {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  vatios?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IPoweSample> = {};
