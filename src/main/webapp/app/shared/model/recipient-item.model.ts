import { Moment } from 'moment';
import { ITask } from 'app/shared/model/task.model';
import { IDocument } from 'app/shared/model/document.model';
import { IItemParticipant } from 'app/shared/model/item-participant.model';
import { IMessage } from 'app/shared/model/message.model';
import { IRecipient } from 'app/shared/model/recipient.model';
import { IMessageItem } from 'app/shared/model/message-item.model';

export interface IRecipientItem {
  id?: number;
  permissionToAll?: boolean;
  whenCreated?: Moment;
  task?: ITask;
  document?: IDocument;
  itemParticipants?: IItemParticipant[];
  messages?: IMessage[];
  recipient?: IRecipient;
  messageItem?: IMessageItem;
}

export const defaultValue: Readonly<IRecipientItem> = {
  permissionToAll: false
};
