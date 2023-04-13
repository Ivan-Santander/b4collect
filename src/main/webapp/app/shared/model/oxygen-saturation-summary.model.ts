import dayjs from 'dayjs';

export interface IOxygenSaturationSummary {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  fieldOxigenSaturationAverage?: number | null;
  fieldOxigenSaturationMax?: number | null;
  fieldOxigenSaturationMin?: number | null;
  fieldSuplementalOxigenFlowRateAverage?: number | null;
  fieldSuplementalOxigenFlowRateMax?: number | null;
  fieldSuplementalOxigenFlowRateMin?: number | null;
  fieldOxigenTherapyAdministrationMode?: number | null;
  fieldOxigenSaturationMode?: number | null;
  fieldOxigenSaturationMeasurementMethod?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IOxygenSaturationSummary> = {};
