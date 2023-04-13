import dayjs from 'dayjs';

export interface INutrition {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  mealType?: number | null;
  food?: string | null;
  nutrients?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<INutrition> = {};
