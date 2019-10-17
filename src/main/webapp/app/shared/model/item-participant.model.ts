import { Moment } from 'moment';
import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { Permission } from 'app/shared/model/enumerations/permission.model';

export interface IItemParticipant {
  id?: number;
  permission?: Permission;
  whenCreated?: Moment;
  projectParticipant?: IProjectParticipant;
  recipientItem?: IRecipientItem;
}

export const defaultValue: Readonly<IItemParticipant> = {};
