import dayjs from 'dayjs';

export interface IDistanceDelta {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  distance?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IDistanceDelta> = {};
