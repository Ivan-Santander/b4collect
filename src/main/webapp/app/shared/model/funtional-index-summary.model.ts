import dayjs from 'dayjs';

export interface IFuntionalIndexSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldFuntionalIndexAverage?: number | null;
  fieldFuntionalIndexMax?: number | null;
  fieldFuntionalIndexMin?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IFuntionalIndexSummary> = {};
