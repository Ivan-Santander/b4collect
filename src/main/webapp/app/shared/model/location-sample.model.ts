import dayjs from 'dayjs';

export interface ILocationSample {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  latitudMin?: number | null;
  longitudMin?: number | null;
  latitudMax?: number | null;
  longitudMax?: number | null;
  accuracy?: number | null;
  altitud?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ILocationSample> = {};
