import dayjs from 'dayjs';

export interface ISpeed {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  speed?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ISpeed> = {};
