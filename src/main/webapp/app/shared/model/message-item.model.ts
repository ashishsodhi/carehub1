import { Moment } from 'moment';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IMessageItem {
  id?: number;
  whenCreated?: Moment;
  recipientItems?: IRecipientItem[];
  message?: IMessage;
}

export const defaultValue: Readonly<IMessageItem> = {};
