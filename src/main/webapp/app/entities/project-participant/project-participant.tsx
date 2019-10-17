import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project-participant.reducer';
import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectParticipantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ProjectParticipant extends React.Component<IProjectParticipantProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projectParticipantList, match } = this.props;
    return (
      <div>
        <h2 id="project-participant-heading">
          Project Participants
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Project Participant
          </Link>
        </h2>
        <div className="table-responsive">
          {projectParticipantList && projectParticipantList.length > 0 ? (
            <Table responsive aria-describedby="project-participant-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Member Id</th>
                  <th>Inviter Id</th>
                  <th>First Name</th>
                  <th>Email Address</th>
                  <th>Relationship To Inviter</th>
                  <th>Permission</th>
                  <th>Status</th>
                  <th>Status TLM</th>
                  <th>When Created</th>
                  <th>Project</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {projectParticipantList.map((projectParticipant, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${projectParticipant.id}`} color="link" size="sm">
                        {projectParticipant.id}
                      </Button>
                    </td>
                    <td>{projectParticipant.memberId}</td>
                    <td>{projectParticipant.inviterId}</td>
                    <td>{projectParticipant.firstName}</td>
                    <td>{projectParticipant.emailAddress}</td>
                    <td>{projectParticipant.relationshipToInviter}</td>
                    <td>{projectParticipant.permission}</td>
                    <td>{projectParticipant.status}</td>
                    <td>
                      <TextFormat type="date" value={projectParticipant.statusTLM} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={projectParticipant.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {projectParticipant.project ? (
                        <Link to={`project/${projectParticipant.project.id}`}>{projectParticipant.project.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${projectParticipant.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectParticipant.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectParticipant.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Project Participants found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ projectParticipant }: IRootState) => ({
  projectParticipantList: projectParticipant.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectParticipant);
