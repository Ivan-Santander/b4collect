import dayjs from 'dayjs';

export interface ICaloriesBMR {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  calorias?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ICaloriesBMR> = {};