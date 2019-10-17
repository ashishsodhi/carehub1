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
import { getEntity, updateEntity, createEntity, reset } from './recipient.reducer';
import { IRecipient } from 'app/shared/model/recipient.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecipientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRecipientUpdateState {
  isNew: boolean;
  projectId: string;
}

export class RecipientUpdate extends React.Component<IRecipientUpdateProps, IRecipientUpdateState> {
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
      const { recipientEntity } = this.props;
      const entity = {
        ...recipientEntity,
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
    this.props.history.push('/entity/recipient');
  };

  render() {
    const { recipientEntity, projects, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.recipient.home.createOrEditLabel">Create or edit a Recipient</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : recipientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="recipient-id">ID</Label>
                    <AvInput id="recipient-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="relationshipToYouLabel" for="recipient-relationshipToYou">
                    Relationship To You
                  </Label>
                  <AvField id="recipient-relationshipToYou" type="text" name="relationshipToYou" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="recipient-status">
                    Status
                  </Label>
                  <AvInput
                    id="recipient-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && recipientEntity.status) || 'DRAFT'}
                  >
                    <option value="DRAFT">DRAFT</option>
                    <option value="INACTIVE">INACTIVE</option>
                    <option value="ACTIVE">ACTIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusTLMLabel" for="recipient-statusTLM">
                    Status TLM
                  </Label>
                  <AvInput
                    id="recipient-statusTLM"
                    type="datetime-local"
                    className="form-control"
                    name="statusTLM"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.recipientEntity.statusTLM)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="recipient-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="recipient-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.recipientEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="recipient-project">Project</Label>
                  <AvInput id="recipient-project" type="select" className="form-control" name="project.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/recipient" replace color="info">
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
  recipientEntity: storeState.recipient.entity,
  loading: storeState.recipient.loading,
  updating: storeState.recipient.updating,
  updateSuccess: storeState.recipient.updateSuccess
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
)(RecipientUpdate);
