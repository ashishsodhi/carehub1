import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ItemParticipant from './item-participant';
import ItemParticipantDetail from './item-participant-detail';
import ItemParticipantUpdate from './item-participant-update';
import ItemParticipantDeleteDialog from './item-participant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ItemParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ItemParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ItemParticipantDetail} />
      <ErrorBoundaryRoute path={match.url} component={ItemParticipant} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ItemParticipantDeleteDialog} />
  </>
);

export default Routes;
