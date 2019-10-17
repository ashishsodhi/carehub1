import { Moment } from 'moment';
import { IRecipient } from 'app/shared/model/recipient.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IConcerns {
  id?: number;
  description?: string;
  status?: Status;
  whenCreated?: Moment;
  recipient?: IRecipient;
}

export const defaultValue: Readonly<IConcerns> = {};
