import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MessageItem from './message-item';
import MessageItemDetail from './message-item-detail';
import MessageItemUpdate from './message-item-update';
import MessageItemDeleteDialog from './message-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MessageItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MessageItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MessageItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={MessageItem} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MessageItemDeleteDialog} />
  </>
);

export default Routes;
