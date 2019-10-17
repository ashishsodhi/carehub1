import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import projectTemplate, {
  ProjectTemplateState
} from 'app/entities/project-template/project-template.reducer';
// prettier-ignore
import project, {
  ProjectState
} from 'app/entities/project/project.reducer';
// prettier-ignore
import projectParticipant, {
  ProjectParticipantState
} from 'app/entities/project-participant/project-participant.reducer';
// prettier-ignore
import recipient, {
  RecipientState
} from 'app/entities/recipient/recipient.reducer';
// prettier-ignore
import concerns, {
  ConcernsState
} from 'app/entities/concerns/concerns.reducer';
// prettier-ignore
import responsibilities, {
  ResponsibilitiesState
} from 'app/entities/responsibilities/responsibilities.reducer';
// prettier-ignore
import itemParticipant, {
  ItemParticipantState
} from 'app/entities/item-participant/item-participant.reducer';
// prettier-ignore
import recipientItem, {
  RecipientItemState
} from 'app/entities/recipient-item/recipient-item.reducer';
// prettier-ignore
import task, {
  TaskState
} from 'app/entities/task/task.reducer';
// prettier-ignore
import document, {
  DocumentState
} from 'app/entities/document/document.reducer';
// prettier-ignore
import message, {
  MessageState
} from 'app/entities/message/message.reducer';
// prettier-ignore
import messageItem, {
  MessageItemState
} from 'app/entities/message-item/message-item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly projectTemplate: ProjectTemplateState;
  readonly project: ProjectState;
  readonly projectParticipant: ProjectParticipantState;
  readonly recipient: RecipientState;
  readonly concerns: ConcernsState;
  readonly responsibilities: ResponsibilitiesState;
  readonly itemParticipant: ItemParticipantState;
  readonly recipientItem: RecipientItemState;
  readonly task: TaskState;
  readonly document: DocumentState;
  readonly message: MessageState;
  readonly messageItem: MessageItemState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  projectTemplate,
  project,
  projectParticipant,
  recipient,
  concerns,
  responsibilities,
  itemParticipant,
  recipientItem,
  task,
  document,
  message,
  messageItem,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
