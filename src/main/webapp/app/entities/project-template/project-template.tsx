import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project-template.reducer';
import { IProjectTemplate } from 'app/shared/model/project-template.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectTemplateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ProjectTemplate extends React.Component<IProjectTemplateProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projectTemplateList, match } = this.props;
    return (
      <div>
        <h2 id="project-template-heading">
          Project Templates
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Project Template
          </Link>
        </h2>
        <div className="table-responsive">
          {projectTemplateList && projectTemplateList.length > 0 ? (
            <Table responsive aria-describedby="project-template-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Service Id</th>
                  <th>Template Description</th>
                  <th>Template Creation Class</th>
                  <th>When Created</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {projectTemplateList.map((projectTemplate, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${projectTemplate.id}`} color="link" size="sm">
                        {projectTemplate.id}
                      </Button>
                    </td>
                    <td>{projectTemplate.serviceId}</td>
                    <td>{projectTemplate.templateDescription}</td>
                    <td>{projectTemplate.templateCreationClass}</td>
                    <td>{projectTemplate.whenCreated}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${projectTemplate.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectTemplate.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectTemplate.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Project Templates found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ projectTemplate }: IRootState) => ({
  projectTemplateList: projectTemplate.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectTemplate);
