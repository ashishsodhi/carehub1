import { Moment } from 'moment';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { IConcerns } from 'app/shared/model/concerns.model';
import { IResponsibilities } from 'app/shared/model/responsibilities.model';
import { IProject } from 'app/shared/model/project.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecipient {
  id?: number;
  relationshipToYou?: string;
  status?: Status;
  statusTLM?: Moment;
  whenCreated?: Moment;
  recipientItems?: IRecipientItem[];
  concerns?: IConcerns[];
  responsibilities?: IResponsibilities[];
  project?: IProject;
}

export const defaultValue: Readonly<IRecipient> = {};
