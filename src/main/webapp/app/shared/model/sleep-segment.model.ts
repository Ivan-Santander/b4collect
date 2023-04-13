import dayjs from 'dayjs';

export interface ISleepSegment {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldSleepSegmentType?: number | null;
  fieldBloodGlucoseSpecimenSource?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<ISleepSegment> = {};
