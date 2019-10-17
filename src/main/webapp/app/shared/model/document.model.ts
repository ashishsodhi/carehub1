import { Moment } from 'moment';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';

export interface IDocument {
  id?: number;
  fileName?: string;
  extension?: string;
  fileContentContentType?: string;
  fileContent?: any;
  fileId?: number;
  whenCreated?: Moment;
  recipientItem?: IRecipientItem;
}

export const defaultValue: Readonly<IDocument> = {};
