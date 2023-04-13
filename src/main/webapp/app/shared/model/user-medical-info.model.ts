import dayjs from 'dayjs';

export interface IUserMedicalInfo {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  hypertension?: boolean | null;
  highGlucose?: boolean | null;
  hiabetes?: boolean | null;
  totalCholesterol?: number | null;
  hdlCholesterol?: number | null;
  medicalMainCondition?: string | null;
  medicalSecundaryCondition?: string | null;
  medicalMainMedication?: string | null;
  medicalSecundaryMedication?: string | null;
  medicalScore?: number | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IUserMedicalInfo> = {
  hypertension: false,
  highGlucose: false,
  hiabetes: false,
};
