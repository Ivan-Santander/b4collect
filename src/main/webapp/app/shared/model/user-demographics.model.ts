import dayjs from 'dayjs';

export interface IUserDemographics {
  id?: string;
  usuarioId?: string | null;
  empresaId?: string | null;
  gender?: string | null;
  dateOfBird?: string | null;
  age?: number | null;
  country?: string | null;
  state?: string | null;
  city?: string | null;
  ethnicity?: string | null;
  income?: string | null;
  maritalStatus?: string | null;
  education?: string | null;
  endTime?: string | null;
}

export const defaultValue: Readonly<IUserDemographics> = {};
