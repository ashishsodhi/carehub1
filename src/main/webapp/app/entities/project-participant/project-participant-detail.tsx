import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './project-participant.reducer';
import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectParticipantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProjectParticipantDetail extends React.Component<IProjectParticipantDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { projectParticipantEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ProjectParticipant [<b>{projectParticipantEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="memberId">Member Id</span>
            </dt>
            <dd>{projectParticipantEntity.memberId}</dd>
            <dt>
              <span id="inviterId">Inviter Id</span>
            </dt>
            <dd>{projectParticipantEntity.inviterId}</dd>
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{projectParticipantEntity.firstName}</dd>
            <dt>
              <span id="emailAddress">Email Address</span>
            </dt>
            <dd>{projectParticipantEntity.emailAddress}</dd>
            <dt>
              <span id="relationshipToInviter">Relationship To Inviter</span>
            </dt>
            <dd>{projectParticipantEntity.relationshipToInviter}</dd>
            <dt>
              <span id="permission">Permission</span>
            </dt>
            <dd>{projectParticipantEntity.permission}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{projectParticipantEntity.status}</dd>
            <dt>
              <span id="statusTLM">Status TLM</span>
            </dt>
            <dd>
              <TextFormat value={projectParticipantEntity.statusTLM} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={projectParticipantEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Project</dt>
            <dd>{projectParticipantEntity.project ? projectParticipantEntity.project.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/project-participant" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/project-participant/${projectParticipantEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ projectParticipant }: IRootState) => ({
  projectParticipantEntity: projectParticipant.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectParticipantDetail);
