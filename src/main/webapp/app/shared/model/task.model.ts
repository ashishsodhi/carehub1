import { Moment } from 'moment';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: number;
  name?: string;
  description?: string;
  category?: string;
  assignedToMember?: number;
  dueDate?: Moment;
  status?: TaskStatus;
  statusTLM?: Moment;
  whenCreated?: Moment;
  recipientItem?: IRecipientItem;
}

export const defaultValue: Readonly<ITask> = {};
