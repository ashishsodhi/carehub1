import { Moment } from 'moment';
import { IItemParticipant } from 'app/shared/model/item-participant.model';
import { IProject } from 'app/shared/model/project.model';
import { Permission } from 'app/shared/model/enumerations/permission.model';
import { ParticipantStatus } from 'app/shared/model/enumerations/participant-status.model';

export interface IProjectParticipant {
  id?: number;
  memberId?: number;
  inviterId?: number;
  firstName?: string;
  emailAddress?: string;
  relationshipToInviter?: string;
  permission?: Permission;
  status?: ParticipantStatus;
  statusTLM?: Moment;
  whenCreated?: Moment;
  itemParticipants?: IItemParticipant[];
  project?: IProject;
}

export const defaultValue: Readonly<IProjectParticipant> = {};
