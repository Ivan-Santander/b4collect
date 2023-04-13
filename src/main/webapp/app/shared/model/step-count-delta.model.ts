import dayjs from 'dayjs';

export interface IStepCountDelta {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  steps?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IStepCountDelta> = {};
