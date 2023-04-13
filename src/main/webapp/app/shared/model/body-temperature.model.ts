import dayjs from 'dayjs';

export interface IBodyTemperature {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldBodyTemperature?: number | null;
  fieldBodyTemperatureMeasureLocation?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBodyTemperature> = {};
