import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Responsibilities from './responsibilities';
import ResponsibilitiesDetail from './responsibilities-detail';
import ResponsibilitiesUpdate from './responsibilities-update';
import ResponsibilitiesDeleteDialog from './responsibilities-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResponsibilitiesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResponsibilitiesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResponsibilitiesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Responsibilities} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ResponsibilitiesDeleteDialog} />
  </>
);

export default Routes;
