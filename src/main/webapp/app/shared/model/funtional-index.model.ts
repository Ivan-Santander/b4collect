import dayjs from 'dayjs';

export interface IFuntionalIndex {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  bodyHealthScore?: number | null;
  mentalHealthScore?: number | null;
  sleepHealthScore?: number | null;
  funtionalIndex?: number | null;
  alarmRiskScore?: number | null;
  startTime?: string | null;
}

export const defaultValue: Readonly<IFuntionalIndex> = {};
