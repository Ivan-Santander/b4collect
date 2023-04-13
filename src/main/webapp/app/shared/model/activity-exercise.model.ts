import dayjs from 'dayjs';

export interface IActivityExercise {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  exercise?: string | null;
  repetitions?: number | null;
  typeResistence?: string | null;
  resistenceKg?: number | null;
  duration?: number | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IActivityExercise> = {};
