import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { getEntity, updateEntity, createEntity, reset } from './project-participant.reducer';
import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjectParticipantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProjectParticipantUpdateState {
  isNew: boolean;
  projectId: string;
}

export class ProjectParticipantUpdate extends React.Component<IProjectParticipantUpdateProps, IProjectParticipantUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      projectId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProjects();
  }

  saveEntity = (event, errors, values) => {
    values.statusTLM = convertDateTimeToServer(values.statusTLM);
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { projectParticipantEntity } = this.props;
      const entity = {
        ...projectParticipantEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/project-participant');
  };

  render() {
    const { projectParticipantEntity, projects, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.projectParticipant.home.createOrEditLabel">Create or edit a ProjectParticipant</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projectParticipantEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="project-participant-id">ID</Label>
                    <AvInput id="project-participant-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="memberIdLabel" for="project-participant-memberId">
                    Member Id
                  </Label>
                  <AvField id="project-participant-memberId" type="string" className="form-control" name="memberId" />
                </AvGroup>
                <AvGroup>
                  <Label id="inviterIdLabel" for="project-participant-inviterId">
                    Inviter Id
                  </Label>
                  <AvField
                    id="project-participant-inviterId"
                    type="string"
                    className="form-control"
                    name="inviterId"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="project-participant-firstName">
                    First Name
                  </Label>
                  <AvField
                    id="project-participant-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailAddressLabel" for="project-participant-emailAddress">
                    Email Address
                  </Label>
                  <AvField
                    id="project-participant-emailAddress"
                    type="text"
                    name="emailAddress"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="relationshipToInviterLabel" for="project-participant-relationshipToInviter">
                    Relationship To Inviter
                  </Label>
                  <AvField id="project-participant-relationshipToInviter" type="text" name="relationshipToInviter" />
                </AvGroup>
                <AvGroup>
                  <Label id="permissionLabel" for="project-participant-permission">
                    Permission
                  </Label>
                  <AvInput
                    id="project-participant-permission"
                    type="select"
                    className="form-control"
                    name="permission"
                    value={(!isNew && projectParticipantEntity.permission) || 'NONE'}
                  >
                    <option value="NONE">NONE</option>
                    <option value="EDIT">EDIT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="project-participant-status">
                    Status
                  </Label>
                  <AvInput
                    id="project-participant-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && projectParticipantEntity.status) || 'INVITED'}
                  >
                    <option value="INVITED">INVITED</option>
                    <option value="PENDING">PENDING</option>
                    <option value="INACTIVE">INACTIVE</option>
                    <option value="ACTIVE">ACTIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusTLMLabel" for="project-participant-statusTLM">
                    Status TLM
                  </Label>
                  <AvInput
                    id="project-participant-statusTLM"
                    type="datetime-local"
                    className="form-control"
                    name="statusTLM"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.projectParticipantEntity.statusTLM)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="project-participant-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="project-participant-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.projectParticipantEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="project-participant-project">Project</Label>
                  <AvInput id="project-participant-project" type="select" className="form-control" name="project.id">
                    <option value="" key="0" />
                    {projects
                      ? projects.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/project-participant" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  projects: storeState.project.entities,
  projectParticipantEntity: storeState.projectParticipant.entity,
  loading: storeState.projectParticipant.loading,
  updating: storeState.projectParticipant.updating,
  updateSuccess: storeState.projectParticipant.updateSuccess
});

const mapDispatchToProps = {
  getProjects,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectParticipantUpdate);
