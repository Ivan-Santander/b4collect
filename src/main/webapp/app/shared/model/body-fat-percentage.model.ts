import dayjs from 'dayjs';

export interface IBodyFatPercentage {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldPorcentage?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IBodyFatPercentage> = {};
