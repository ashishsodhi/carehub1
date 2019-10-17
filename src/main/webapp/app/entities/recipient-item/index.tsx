import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecipientItem from './recipient-item';
import RecipientItemDetail from './recipient-item-detail';
import RecipientItemUpdate from './recipient-item-update';
import RecipientItemDeleteDialog from './recipient-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecipientItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecipientItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecipientItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={RecipientItem} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RecipientItemDeleteDialog} />
  </>
);

export default Routes;
