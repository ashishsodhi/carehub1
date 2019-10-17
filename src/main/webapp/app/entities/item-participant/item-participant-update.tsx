import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProjectParticipant } from 'app/shared/model/project-participant.model';
import { getEntities as getProjectParticipants } from 'app/entities/project-participant/project-participant.reducer';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { getEntities as getRecipientItems } from 'app/entities/recipient-item/recipient-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './item-participant.reducer';
import { IItemParticipant } from 'app/shared/model/item-participant.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IItemParticipantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IItemParticipantUpdateState {
  isNew: boolean;
  projectParticipantId: string;
  recipientItemId: string;
}

export class ItemParticipantUpdate extends React.Component<IItemParticipantUpdateProps, IItemParticipantUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      projectParticipantId: '0',
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

    this.props.getProjectParticipants();
    this.props.getRecipientItems();
  }

  saveEntity = (event, errors, values) => {
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { itemParticipantEntity } = this.props;
      const entity = {
        ...itemParticipantEntity,
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
    this.props.history.push('/entity/item-participant');
  };

  render() {
    const { itemParticipantEntity, projectParticipants, recipientItems, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.itemParticipant.home.createOrEditLabel">Create or edit a ItemParticipant</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : itemParticipantEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="item-participant-id">ID</Label>
                    <AvInput id="item-participant-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="permissionLabel" for="item-participant-permission">
                    Permission
                  </Label>
                  <AvInput
                    id="item-participant-permission"
                    type="select"
                    className="form-control"
                    name="permission"
                    value={(!isNew && itemParticipantEntity.permission) || 'NONE'}
                  >
                    <option value="NONE">NONE</option>
                    <option value="EDIT">EDIT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="item-participant-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="item-participant-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.itemParticipantEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="item-participant-projectParticipant">Project Participant</Label>
                  <AvInput id="item-participant-projectParticipant" type="select" className="form-control" name="projectParticipant.id">
                    <option value="" key="0" />
                    {projectParticipants
                      ? projectParticipants.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="item-participant-recipientItem">Recipient Item</Label>
                  <AvInput id="item-participant-recipientItem" type="select" className="form-control" name="recipientItem.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/item-participant" replace color="info">
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
  projectParticipants: storeState.projectParticipant.entities,
  recipientItems: storeState.recipientItem.entities,
  itemParticipantEntity: storeState.itemParticipant.entity,
  loading: storeState.itemParticipant.loading,
  updating: storeState.itemParticipant.updating,
  updateSuccess: storeState.itemParticipant.updateSuccess
});

const mapDispatchToProps = {
  getProjectParticipants,
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
)(ItemParticipantUpdate);
