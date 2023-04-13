export interface IUserBodyInfo {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  waistCircumference?: number | null;
  hipCircumference?: number | null;
  chestCircumference?: number | null;
  boneCompositionPercentaje?: number | null;
  muscleCompositionPercentaje?: number | null;
  smoker?: boolean | null;
  waightKg?: number | null;
  heightCm?: number | null;
  bodyHealthScore?: number | null;
  cardiovascularRisk?: number | null;
}

export const defaultValue: Readonly<IUserBodyInfo> = {
  smoker: false,
};
