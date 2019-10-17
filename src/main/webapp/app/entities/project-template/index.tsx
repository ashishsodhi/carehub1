import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectTemplate from './project-template';
import ProjectTemplateDetail from './project-template-detail';
import ProjectTemplateUpdate from './project-template-update';
import ProjectTemplateDeleteDialog from './project-template-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectTemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectTemplate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProjectTemplateDeleteDialog} />
  </>
);

export default Routes;
