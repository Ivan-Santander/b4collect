export interface ISleepScores {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  sleepQualityRatingScore?: number | null;
  sleepEfficiencyScore?: number | null;
  sleepGooalSecondsScore?: number | null;
  sleepContinuityScore?: number | null;
  sleepContinuityRating?: number | null;
}

export const defaultValue: Readonly<ISleepScores> = {};
