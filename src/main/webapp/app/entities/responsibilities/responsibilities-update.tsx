import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRecipient } from 'app/shared/model/recipient.model';
import { getEntities as getRecipients } from 'app/entities/recipient/recipient.reducer';
import { getEntity, updateEntity, createEntity, reset } from './responsibilities.reducer';
import { IResponsibilities } from 'app/shared/model/responsibilities.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResponsibilitiesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IResponsibilitiesUpdateState {
  isNew: boolean;
  recipientId: string;
}

export class ResponsibilitiesUpdate extends React.Component<IResponsibilitiesUpdateProps, IResponsibilitiesUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      recipientId: '0',
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

    this.props.getRecipients();
  }

  saveEntity = (event, errors, values) => {
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { responsibilitiesEntity } = this.props;
      const entity = {
        ...responsibilitiesEntity,
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
    this.props.history.push('/entity/responsibilities');
  };

  render() {
    const { responsibilitiesEntity, recipients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.responsibilities.home.createOrEditLabel">Create or edit a Responsibilities</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : responsibilitiesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="responsibilities-id">ID</Label>
                    <AvInput id="responsibilities-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="descriptionLabel" for="responsibilities-description">
                    Description
                  </Label>
                  <AvField
                    id="responsibilities-description"
                    type="text"
                    name="description"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="responsibilities-status">
                    Status
                  </Label>
                  <AvInput
                    id="responsibilities-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && responsibilitiesEntity.status) || 'DRAFT'}
                  >
                    <option value="DRAFT">DRAFT</option>
                    <option value="INACTIVE">INACTIVE</option>
                    <option value="ACTIVE">ACTIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="responsibilities-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="responsibilities-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.responsibilitiesEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="responsibilities-recipient">Recipient</Label>
                  <AvInput id="responsibilities-recipient" type="select" className="form-control" name="recipient.id">
                    <option value="" key="0" />
                    {recipients
                      ? recipients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/responsibilities" replace color="info">
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
  recipients: storeState.recipient.entities,
  responsibilitiesEntity: storeState.responsibilities.entity,
  loading: storeState.responsibilities.loading,
  updating: storeState.responsibilities.updating,
  updateSuccess: storeState.responsibilities.updateSuccess
});

const mapDispatchToProps = {
  getRecipients,
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
)(ResponsibilitiesUpdate);
