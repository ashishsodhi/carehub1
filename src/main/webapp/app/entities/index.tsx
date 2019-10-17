import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectTemplate from './project-template';
import Project from './project';
import ProjectParticipant from './project-participant';
import Recipient from './recipient';
import Concerns from './concerns';
import Responsibilities from './responsibilities';
import ItemParticipant from './item-participant';
import RecipientItem from './recipient-item';
import Task from './task';
import Document from './document';
import Message from './message';
import MessageItem from './message-item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/project-template`} component={ProjectTemplate} />
      <ErrorBoundaryRoute path={`${match.url}/project`} component={Project} />
      <ErrorBoundaryRoute path={`${match.url}/project-participant`} component={ProjectParticipant} />
      <ErrorBoundaryRoute path={`${match.url}/recipient`} component={Recipient} />
      <ErrorBoundaryRoute path={`${match.url}/concerns`} component={Concerns} />
      <ErrorBoundaryRoute path={`${match.url}/responsibilities`} component={Responsibilities} />
      <ErrorBoundaryRoute path={`${match.url}/item-participant`} component={ItemParticipant} />
      <ErrorBoundaryRoute path={`${match.url}/recipient-item`} component={RecipientItem} />
      <ErrorBoundaryRoute path={`${match.url}/task`} component={Task} />
      <ErrorBoundaryRoute path={`${match.url}/document`} component={Document} />
      <ErrorBoundaryRoute path={`${match.url}/message`} component={Message} />
      <ErrorBoundaryRoute path={`${match.url}/message-item`} component={MessageItem} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
