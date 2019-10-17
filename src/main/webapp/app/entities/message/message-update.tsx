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
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { getEntities as getRecipientItems } from 'app/entities/recipient-item/recipient-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './message.reducer';
import { IMessage } from 'app/shared/model/message.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMessageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMessageUpdateState {
  isNew: boolean;
  projectId: string;
  recipientItemId: string;
}

export class MessageUpdate extends React.Component<IMessageUpdateProps, IMessageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      projectId: '0',
      recipientItemId: '0',
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
    this.props.getRecipientItems();
  }

  saveEntity = (event, errors, values) => {
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { messageEntity } = this.props;
      const entity = {
        ...messageEntity,
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
    this.props.history.push('/entity/message');
  };

  render() {
    const { messageEntity, projects, recipientItems, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.message.home.createOrEditLabel">Create or edit a Message</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : messageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="message-id">ID</Label>
                    <AvInput id="message-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="recipientIdLabel" for="message-recipientId">
                    Recipient Id
                  </Label>
                  <AvField id="message-recipientId" type="string" className="form-control" name="recipientId" />
                </AvGroup>
                <AvGroup>
                  <Label id="postedByMemberIdLabel" for="message-postedByMemberId">
                    Posted By Member Id
                  </Label>
                  <AvField
                    id="message-postedByMemberId"
                    type="string"
                    className="form-control"
                    name="postedByMemberId"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="messageBodyLabel" for="message-messageBody">
                    Message Body
                  </Label>
                  <AvField id="message-messageBody" type="text" name="messageBody" />
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="message-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="message-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.messageEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="message-project">Project</Label>
                  <AvInput id="message-project" type="select" className="form-control" name="project.id">
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
                <AvGroup>
                  <Label for="message-recipientItem">Recipient Item</Label>
                  <AvInput id="message-recipientItem" type="select" className="form-control" name="recipientItem.id">
                    <option value="" key="0" />
                    {recipientItems
                      ? recipientItems.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/message" replace color="info">
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
  recipientItems: storeState.recipientItem.entities,
  messageEntity: storeState.message.entity,
  loading: storeState.message.loading,
  updating: storeState.message.updating,
  updateSuccess: storeState.message.updateSuccess
});

const mapDispatchToProps = {
  getProjects,
  getRecipientItems,
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
)(MessageUpdate);
