import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Recipient from './recipient';
import RecipientDetail from './recipient-detail';
import RecipientUpdate from './recipient-update';
import RecipientDeleteDialog from './recipient-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecipientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecipientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecipientDetail} />
      <ErrorBoundaryRoute path={match.url} component={Recipient} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RecipientDeleteDialog} />
  </>
);

export default Routes;
