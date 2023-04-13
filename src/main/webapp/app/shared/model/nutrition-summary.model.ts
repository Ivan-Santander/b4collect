import dayjs from 'dayjs';

export interface INutritionSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  mealType?: string | null;
  nutrient?: string | null;
  startTime?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<INutritionSummary> = {};
