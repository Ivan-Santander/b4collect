import dayjs from 'dayjs';

export interface ICyclingWheelRevolution {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  revolutions?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ICyclingWheelRevolution> = {};
