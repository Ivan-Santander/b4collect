import dayjs from 'dayjs';

export interface IHeight {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldHeight?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IHeight> = {};
