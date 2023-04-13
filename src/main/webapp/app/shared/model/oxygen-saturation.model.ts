import dayjs from 'dayjs';

export interface IOxygenSaturation {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldOxigenSaturation?: number | null;
  fieldSuplementalOxigenFlowRate?: number | null;
  fieldOxigenTherapyAdministrationMode?: number | null;
  fieldOxigenSaturationMode?: number | null;
  fieldOxigenSaturationMeasurementMethod?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IOxygenSaturation> = {};
