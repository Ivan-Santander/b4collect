import dayjs from 'dayjs';

export interface IWeight {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldWeight?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IWeight> = {};
