import dayjs from 'dayjs';

export interface IStepCountCadence {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  rpm?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IStepCountCadence> = {};
