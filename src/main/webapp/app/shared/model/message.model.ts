import { Moment } from 'moment';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { IProject } from 'app/shared/model/project.model';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';

export interface IMessage {
  id?: number;
  recipientId?: number;
  postedByMemberId?: number;
  messageBody?: string;
  whenCreated?: Moment;
  messageItems?: IMessageItem[];
  project?: IProject;
  recipientItem?: IRecipientItem;
}

export const defaultValue: Readonly<IMessage> = {};
