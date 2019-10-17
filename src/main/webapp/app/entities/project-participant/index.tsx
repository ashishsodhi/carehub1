import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectParticipant from './project-participant';
import ProjectParticipantDetail from './project-participant-detail';
import ProjectParticipantUpdate from './project-participant-update';
import ProjectParticipantDeleteDialog from './project-participant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectParticipantDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectParticipant} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProjectParticipantDeleteDialog} />
  </>
);

export default Routes;
