import dayjs from 'dayjs';

export interface IAlarmRiskIndexSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldAlarmRiskAverage?: number | null;
  fieldAlarmRiskMax?: number | null;
  fieldAlarmRiskMin?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IAlarmRiskIndexSummary> = {};
