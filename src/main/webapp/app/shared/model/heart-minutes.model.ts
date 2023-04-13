import dayjs from 'dayjs';

export interface IHeartMinutes {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  severity?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IHeartMinutes> = {};
