import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Concerns from './concerns';
import ConcernsDetail from './concerns-detail';
import ConcernsUpdate from './concerns-update';
import ConcernsDeleteDialog from './concerns-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConcernsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConcernsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConcernsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Concerns} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ConcernsDeleteDialog} />
  </>
);

export default Routes;
