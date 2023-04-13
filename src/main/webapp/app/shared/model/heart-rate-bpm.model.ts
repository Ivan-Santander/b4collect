import dayjs from 'dayjs';

export interface IHeartRateBpm {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  ppm?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IHeartRateBpm> = {};
