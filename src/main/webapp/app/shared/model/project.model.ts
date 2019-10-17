import { Moment } from 'moment';
import { IRecipient } from 'app/shared/model/recipient.model';
import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { IMessage } from 'app/shared/model/message.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IProject {
  id?: number;
  memberId?: number;
  createdByMemberId?: number;
  serviceId?: number;
  status?: Status;
  statusTLM?: Moment;
  whenCreated?: Moment;
  recipients?: IRecipient[];
  projectParticipants?: IProjectParticipant[];
  messages?: IMessage[];
}

export const defaultValue: Readonly<IProject> = {};
