import dayjs from 'dayjs';

export interface IActivitySummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldActivity?: number | null;
  fieldDuration?: number | null;
  fieldNumSegments?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IActivitySummary> = {};
